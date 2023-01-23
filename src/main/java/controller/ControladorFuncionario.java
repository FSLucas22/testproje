package controller;

import model.ModeloFuncionario;
import model.entities.Funcionario;
import view.VisualizadorFuncionario;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ControladorFuncionario {
    private final ModeloFuncionario modelo;
    private final VisualizadorFuncionario visualizador;

    public ControladorFuncionario(ModeloFuncionario modelo, VisualizadorFuncionario visualizador) {
        this.modelo = modelo;
        this.visualizador = visualizador;
    }
    public void cadastraFuncionario(String nome, LocalDate dataNascimento,
                                           BigDecimal salario, String funcao)  throws SQLException {
        var funcionario = modelo.cadastraFuncionario(nome, dataNascimento, salario, funcao);
        visualizador.cadastraFuncionario(funcionario);
    }
    public void deletaFuncionarioPorNome(String nome) throws SQLException {
        modelo.deletaFuncionarioPorNome(nome);
        visualizador.deletaFuncionarioPorNome(nome);
    }
    public void listaFuncionarios() throws SQLException {
        var funcionarios = modelo.listaFuncionarios();
        visualizador.listaFuncionarios(funcionarios);
    }
    public void atualizaFuncionario(Funcionario funcionario) throws SQLException {
        var funcionarioAtualizado = modelo.atualizaFuncionario(funcionario);
        visualizador.atualizaFuncionario(funcionario, funcionarioAtualizado);
    }
    public void atualizaFuncionarios(List<Funcionario> funcionarios) throws SQLException {
        var funcionariosAtualizados = modelo.atualizaFuncionarios(funcionarios);
        visualizador.atualizaFuncionarios(funcionarios, funcionariosAtualizados);
    }
    public void atualizaSalarioDeTodos(double porcentagem) throws SQLException {
        var funcionarios = modelo.atualizaSalarioDeTodos(porcentagem);
        visualizador.atualizaSalarioDeTodos(funcionarios, porcentagem);
    }
}
