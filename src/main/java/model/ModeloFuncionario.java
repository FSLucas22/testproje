package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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
    public List<Funcionario> listaFuncionariosPorNome() throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY nome"
        );
        return criaFuncionariosPorResultSet(resultadoQuery);
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
}
