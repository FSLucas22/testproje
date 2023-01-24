package controller;

import model.IModeloFuncionario;
import model.entities.Funcionario;
import view.IVisualizadorFuncionario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IControladorFuncionario extends
        IControlador<Funcionario, IModeloFuncionario, IVisualizadorFuncionario>
{
    default List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException {
        var funcionarios = getModelo().atualizarSalarioDeTodos(porcentagem);
        getVisualizador().atualizarSalarioDeTodos(funcionarios, porcentagem);
        return funcionarios;
    }
    default Map<String, List<Funcionario>> agruparPorFuncao() throws SQLException {
        var grupos = getModelo().agruparPorFuncao();
        getVisualizador().agruparPorFuncao(grupos);
        return grupos;
    }
    default BigDecimal retornarTotalSalarios() throws SQLException {
        var total = getModelo().retornarTotalSalarios();
        getVisualizador().retornarTotalSalarios(total);
        return total;
    }
    default Map<Funcionario, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException {
        var relacao = getModelo().listarComSalariosMinimos(salarioMinimo);
        getVisualizador().listarComSalariosMinimos(salarioMinimo, relacao);
        return relacao;
    }
    default List<Funcionario> listarAniversariantesDosMeses(int... meses) throws SQLException {
        var aniversariantes = getModelo().listarAniversariantesDosMeses(meses);
        getVisualizador().listarAniversariantesDosMeses(aniversariantes, meses);
        return aniversariantes;
    }
    default List<String> retornarNomeIdadeFuncionarioMaisVelho() throws SQLException {
        var dados = getModelo().retornarNomeIdadeFuncionarioMaisVelho();
        getVisualizador().retornarNomeIdadeFuncionarioMaisVelho(dados);
        return dados;
    }
}
