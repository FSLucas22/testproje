package model;

import model.entities.Pessoa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloPessoa implements Modelo<Pessoa> {
    private final Connection conexao;
    public ModeloPessoa(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Pessoa> criarPorResultSet(ResultSet resultSet) throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            LocalDate dataNascimento = resultSet.getObject("data_nascimento", LocalDate.class);
            Pessoa pessoa = new Pessoa(id, nome, dataNascimento);
            pessoas.add(pessoa);
        }
        return pessoas;
    }
    @Override
    public Pessoa cadastrar(Pessoa pessoa) throws SQLException {
        String [] toReturn = {"id"};
        try (PreparedStatement comando = conexao.prepareStatement(
                "INSERT INTO Pessoa(nome, data_nascimento) VALUES (?, ?)",
                toReturn
        )
        ) {
            var nome = pessoa.getNome();
            var dataNascimento = pessoa.getDataNascimento();
            comando.setString(1, nome);
            comando.setObject(2, dataNascimento);
            comando.executeUpdate();
            ResultSet campos_gerados = comando.getGeneratedKeys();
            campos_gerados.next();
            int id = campos_gerados.getInt(1);
            return new Pessoa(id, nome, dataNascimento);
        }
    }
    @Override
    public void deletarPorNome(String nome) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM Pessoa WHERE nome = ?"
        )
        ) {
            comando.setString(1, nome);
            comando.executeUpdate();
        }
    }
    @Override
    public List<Pessoa> listar() throws SQLException{
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Pessoa"
        );
        return criarPorResultSet(resultadoQuery);
    }

    @Override
    public List<Pessoa> listarPorNome(Ordem ordem) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Pessoa ORDER BY nome " + ordem
        );
        return criarPorResultSet(resultadoQuery);
    }

    @Override
    public Pessoa retornarPorId(int id) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Pessoa WHERE id = " + id
        );
        return criarPorResultSet(resultadoQuery).get(0);
    }
    @Override
    public Pessoa atualizar(Pessoa pessoa) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "UPDATE Pessoa SET nome=?, data_nascimento=? WHERE id = ?"
        )
        ) {
            comando.setString(1, pessoa.getNome());
            comando.setObject(2, pessoa.getDataNascimento());
            comando.setInt(3, pessoa.getId());
            comando.executeUpdate();
            return retornarPorId(pessoa.getId());
        }
    }
    public Connection getConexao() {
        return conexao;
    }
}
