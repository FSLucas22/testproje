package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloFuncionario {
    private final Connection conexao;
    public ModeloFuncionario(Connection conexao) {
        this.conexao = conexao;
    }
    private List<Funcionario> criaFuncionariosPorResultSet(ResultSet resultSet) throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            LocalDate dataNascimento = resultSet.getObject("data_nascimento", LocalDate.class);
            BigDecimal salario = resultSet.getBigDecimal("salario");
            String funcao = resultSet.getString("funcao");
            Funcionario funcionario = new Funcionario(id, nome, dataNascimento, salario, funcao);
            funcionarios.add(funcionario);
        }
        return funcionarios;
    }
    public Funcionario cadastraFuncionario(String nome, LocalDate dataNascimento,
                                           BigDecimal salario, String funcao)  throws SQLException {
        String[] toReturn = {"id"};
        try (PreparedStatement comando = conexao.prepareStatement(
                "INSERT INTO Funcionario(nome, data_nascimento, salario, funcao) VALUES (?, ?, ?, ?)",
                toReturn
        )
        ) {
            comando.setString(1, nome);
            comando.setObject(2, dataNascimento);
            comando.setObject(3, salario);
            comando.setString(4, funcao);
            comando.executeUpdate();
            ResultSet campos_gerados = comando.getGeneratedKeys();
            campos_gerados.next();
            int id = campos_gerados.getInt(1);
            return new Funcionario(id, nome, dataNascimento, salario, funcao);
        }
    }
    public void deletaFuncionarioPorNome(String nome) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM Funcionario WHERE nome = ?"
        )
        ) {
            comando.setString(1, nome);
            comando.executeUpdate();
        }
    }
    public List<Funcionario> listaFuncionarios() throws SQLException{
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario"
        );
        return criaFuncionariosPorResultSet(resultadoQuery);
    }
    public Funcionario retornaFuncionarPorId(int id) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario WHERE id = " + id
        );
        return criaFuncionariosPorResultSet(resultadoQuery).get(0);
    }
    public Map<String, List<Funcionario>> agrupaFuncionariosPorFuncao() throws SQLException {
        Map<String, List<Funcionario>> grupos = new HashMap<>();
        for (Funcionario funcionario : listaFuncionarios()) {
            var funcao = funcionario.getFuncao();
            if (!grupos.containsKey(funcao)) {
                grupos.put(funcao, new ArrayList<>());
            }
            grupos.get(funcao).add(funcionario);
        }
        return grupos;
    }
    public List<Funcionario> listaFuncionariosPorNome(Order order) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY nome " + order
        );
        return criaFuncionariosPorResultSet(resultadoQuery);
    }
    public List<Funcionario> listaFuncionariosPorNome() throws SQLException {
        Statement comando = conexao.createStatement();
        return listaFuncionariosPorNome(Order.ASC);
    }
    public Funcionario atualizaFuncionario(Funcionario funcionario) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "UPDATE Funcionario SET nome=?, data_nascimento=?, salario=?, funcao=? WHERE id = ?"
        )
        ) {
            comando.setString(1, funcionario.getNome());
            comando.setObject(2, funcionario.getDataNascimento());
            comando.setObject(3, funcionario.getSalario());
            comando.setString(4, funcionario.getFuncao());
            comando.setInt(5, funcionario.getId());
            comando.executeUpdate();
            return retornaFuncionarPorId(funcionario.getId());
        }
    }
    public List<Funcionario> atualizaFuncionarios(List<Funcionario> funcionarios) throws SQLException {
        List<Funcionario> listaAtualizada = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            var funcionarioAtualizado = atualizaFuncionario(funcionario);
            listaAtualizada.add(funcionarioAtualizado);
        }
        return listaAtualizada;
    }
    public List<Funcionario> atualizaSalarioDeTodos(double porcentagem) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "UPDATE Funcionario SET salario=(salario+ (salario * ? / 100))"
        )
        ) {
            comando.setBigDecimal(1, new BigDecimal(porcentagem));
            comando.executeUpdate();
            return listaFuncionarios();
        }
    }
    public BigDecimal retornaTotalSalarios() throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT SUM(salario) FROM Funcionario"
        );
        resultadoQuery.next();
        return resultadoQuery.getBigDecimal(1);
    }
    public Map<String, BigDecimal> salariosMinimos(double salarioMinimo) throws SQLException {
        Map<String, BigDecimal> relacaoNomeSalarioMinimo = new HashMap<>();
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT nome, salario / " + salarioMinimo + " FROM Funcionario"
        );
        while (resultadoQuery.next()) {
            var nome = resultadoQuery.getString(1);
            var quantSalariosMinimos = resultadoQuery.getBigDecimal(2);
            relacaoNomeSalarioMinimo.put(nome, quantSalariosMinimos);
        }
        return relacaoNomeSalarioMinimo;
    }
    public List<Funcionario> aniversariantesDosMeses(int ... meses) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM Funcionario WHERE EXTRACT(MONTH FROM data_nascimento) in (");
        for (int i=0; i<meses.length; i++) {
            query.append(meses[i]).append((i == meses.length - 1) ? ")" : ",");
        }
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                query.toString()
        );
        return criaFuncionariosPorResultSet(resultadoQuery);
    }
    public List<Funcionario> listaFuncionariosPorDataNascimento(Order order) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY data_nascimento " + order
        );
        return criaFuncionariosPorResultSet(resultadoQuery);
    }
    public List<Funcionario> listaFuncionariosPorDataNascimento() throws SQLException {
        return listaFuncionariosPorDataNascimento(Order.ASC);
    }
    public Funcionario retornaFuncionarioMaisVelho() throws SQLException {
        return listaFuncionariosPorDataNascimento().get(0);
    }
    public List<String> retornaNomeIdadeFuncionarioMaisVelho() throws SQLException {
        var funcionarioMaisVelho = retornaFuncionarioMaisVelho();
        var nome = funcionarioMaisVelho.getNome();
        var idade = calculaIdadePorDataNascimento(funcionarioMaisVelho.getDataNascimento());
        return List.of(nome, Integer.toString(idade));
    }
    public static int calculaIdadePorDataNascimento(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
