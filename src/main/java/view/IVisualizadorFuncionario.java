package view;

import model.Order;
import model.entities.Funcionario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IVisualizadorFuncionario extends IVisualizador<Funcionario> {
    void atualizarSalarioDeTodos(List<Funcionario> funcionariosAtualizados, double porcentagem);
    void retornarTotalSalarios(BigDecimal total);
    void listarComSalariosMinimos(double salarioMinimo, Map<String, BigDecimal> relacaoNomeSalario);
    void listarAniversariantesDosMeses(List<Funcionario> funcionarios, int ... meses);
    void retornarNomeIdadeFuncionarioMaisVelho(List<String> nomeIdadeFuncionarioMaisVelho);
    void agruparPorFuncao(Map<String, List<Funcionario>> relacaoFuncaoFuncionarios);
}
