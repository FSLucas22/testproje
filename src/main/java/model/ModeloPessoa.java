package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModeloPessoa {
    private Connection conexao;
    public ModeloPessoa(Connection conexao) {
        this.conexao = conexao;
    }
    public Pessoa cadastraPessoa(String nome, LocalDate dataNascimento) throws SQLException {
        String [] toReturn = {"id"};
        try (PreparedStatement comando = conexao.prepareStatement(
                "INSERT INTO Pessoa(nome, data_nascimento) VALUES (?, ?)",
                toReturn
        )
        ) {
            comando.setString(1, nome);
            comando.setObject(2, dataNascimento);
            comando.executeUpdate();
            ResultSet campos_gerados = comando.getGeneratedKeys();
            campos_gerados.next();
            int id = campos_gerados.getInt(1);
            return new Pessoa(id, nome, dataNascimento);
        }
    }
    public void deletaPessoaPorNome(String nome) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM Pessoa WHERE nome = ?"
        )
        ) {
            comando.setString(1, nome);
            comando.executeUpdate();
        }
    }
    public List<Pessoa> listaPessoas() throws SQLException{
        List<Pessoa> pessoas = new ArrayList<>();
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Pessoa"
        );
        while (resultadoQuery.next()) {
            int id = resultadoQuery.getInt("id");
            String nome = resultadoQuery.getString("nome");
            LocalDate dataNascimento = resultadoQuery.getObject("data_nascimento", LocalDate.class);
            Pessoa pessoa = new Pessoa(id, nome, dataNascimento);
            pessoas.add(pessoa);
        }
        return pessoas;
    }
    public Connection getConexao() {
        return conexao;
    }
}
