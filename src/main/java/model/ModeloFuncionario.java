package model;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloFuncionario implements IModeloFuncionario{
    private final Connection conexao;
    public ModeloFuncionario(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Funcionario> criarPorResultSet(ResultSet resultSet) throws SQLException {
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
    @Override
    public Funcionario cadastrar(Funcionario funcionario)  throws SQLException {
        String[] toReturn = {"id"};
        try (PreparedStatement comando = conexao.prepareStatement(
                "INSERT INTO Funcionario(nome, data_nascimento, salario, funcao) VALUES (?, ?, ?, ?)",
                toReturn
        )
        ) {
            var nome = funcionario.getNome();
            var dataNascimento = funcionario.getDataNascimento();
            var salario = funcionario.getSalario();
            var funcao = funcionario.getFuncao();
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
    @Override
    public void deletarPorNome(String nome) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM Funcionario WHERE nome = ?"
        )
        ) {
            comando.setString(1, nome);
            comando.executeUpdate();
        }
    }
    @Override
    public List<Funcionario> listar() throws SQLException{
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario"
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public Funcionario retornarPorId(int id) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario WHERE id = " + id
        );
        return criarPorResultSet(resultadoQuery).get(0);
    }
    @Override
    public List<Funcionario> listarPorNome(Order order) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY nome " + order
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public Funcionario atualizar(Funcionario funcionario) throws SQLException {
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
            return retornarPorId(funcionario.getId());
        }
    }
    @Override
    public List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "UPDATE Funcionario SET salario=(salario+ (salario * ? / 100))"
        )
        ) {
            comando.setBigDecimal(1, new BigDecimal(porcentagem));
            comando.executeUpdate();
            return listar();
        }
    }
    @Override
    public BigDecimal retornarTotalSalarios() throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT SUM(salario) FROM Funcionario"
        );
        resultadoQuery.next();
        return resultadoQuery.getBigDecimal(1);
    }
    @Override
    public Map<String, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException {
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
    @Override
    public List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException {
        StringBuilder query = new StringBuilder(
                "SELECT * FROM Funcionario WHERE EXTRACT(MONTH FROM data_nascimento) in (");
        for (int i=0; i<meses.length; i++) {
            query.append(meses[i]).append((i == meses.length - 1) ? ")" : ",");
        }
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                query.toString()
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public List<Funcionario> listarFuncionariosPorDataNascimento(Order order) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY data_nascimento " + order
        );
        return criarPorResultSet(resultadoQuery);
    }
}
