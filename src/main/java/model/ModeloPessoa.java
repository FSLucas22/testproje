package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ModeloPessoa {
    private Connection conexao;
    public ModeloPessoa(Connection conexao) {
        this.conexao = conexao;
    }
    public Pessoa criaPessoa(String nome, LocalDate dataNascimento) throws SQLException {
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
    public Connection getConexao() {
        return conexao;
    }
}