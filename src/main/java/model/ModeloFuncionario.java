package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModeloFuncionario {
    private Connection conexao;
    public ModeloFuncionario(Connection conexao) {
        this.conexao = conexao;
    }
    public Funcionario criaFuncionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) throws SQLException {
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
}
