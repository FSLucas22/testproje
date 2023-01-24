package view;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IVisualizadorFuncionario extends IVisualizador<Funcionario> {
    // Exibe os valores atualizados dos funcionários
    void atualizarSalarioDeTodos(List<Funcionario> funcionariosAtualizados, double porcentagem);

    // Exibe o total da soma dos salários de todos os funcionários
    void retornarTotalSalarios(BigDecimal total);

    // Exibe um relatório relacionando o funcionário à quantidade de salários mínimos que recebe
    void listarComSalariosMinimos(double salarioMinimo, Map<Funcionario, BigDecimal> relacaoNomeSalario);

    // Exibe somete os funcionários que fazem aniversário em um dos meses especificados
    void listarAniversariantesDosMeses(List<Funcionario> funcionarios, int ... meses);

    // Exibe o nome e a idade do funcionário mais velho
    void retornarNomeIdadeFuncionarioMaisVelho(List<String> nomeIdadeFuncionarioMaisVelho);

    // Exibe os funcionários agrupados por função
    void agruparPorFuncao(Map<String, List<Funcionario>> relacaoFuncaoFuncionarios);
}
