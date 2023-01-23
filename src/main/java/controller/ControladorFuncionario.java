package controller;

import model.ModeloFuncionario;
import model.entities.Funcionario;
import view.VisualizadorFuncionario;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ControladorFuncionario {
    private final ModeloFuncionario modelo;
    private final VisualizadorFuncionario visualizador;

    public ControladorFuncionario(ModeloFuncionario modelo, VisualizadorFuncionario visualizador) {
        this.modelo = modelo;
        this.visualizador = visualizador;
    }
    public Funcionario cadastraFuncionario(String nome, LocalDate dataNascimento,
                                           BigDecimal salario, String funcao)  throws SQLException {
        var funcionario = modelo.cadastraFuncionario(nome, dataNascimento, salario, funcao);
        visualizador.cadastraFuncionario(funcionario);
        return funcionario;
    }
    public void deletaFuncionarioPorNome(String nome) throws SQLException {
        modelo.deletaFuncionarioPorNome(nome);
        visualizador.deletaFuncionarioPorNome(nome);
    }
    public List<Funcionario> listaFuncionarios() throws SQLException {
        var funcionarios = modelo.listaFuncionarios();
        visualizador.listaFuncionarios(funcionarios);
        return funcionarios;
    }
    public Funcionario atualizaFuncionario(Funcionario funcionario) throws SQLException {
        var funcionarioAtualizado = modelo.atualizaFuncionario(funcionario);
        visualizador.atualizaFuncionario(funcionario, funcionarioAtualizado);
        return funcionarioAtualizado;
    }
    public List<Funcionario> atualizaFuncionarios(List<Funcionario> funcionarios) throws SQLException {
        var funcionariosAtualizados = modelo.atualizaFuncionarios(funcionarios);
        visualizador.atualizaFuncionarios(funcionarios, funcionariosAtualizados);
        return funcionariosAtualizados;
    }
    public List<Funcionario> atualizaSalarioDeTodos(double porcentagem) throws SQLException {
        var funcionarios = modelo.atualizaSalarioDeTodos(porcentagem);
        visualizador.atualizaSalarioDeTodos(funcionarios, porcentagem);
        return funcionarios;
    }
    public Map<String, List<Funcionario>> agrupaFuncionariosPorFuncao() throws SQLException {
        var grupos = modelo.agrupaFuncionariosPorFuncao();
        visualizador.agrupaFuncionariosPorFuncao(grupos);
        return grupos;
    }
    public List<Funcionario> listaFuncionariosPorNome() throws SQLException {
        var funcionarios = modelo.listaFuncionariosPorNome();
        visualizador.listaFuncionariosPorNome(funcionarios);
        return funcionarios;
    }
}
