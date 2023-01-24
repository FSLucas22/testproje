package model;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface IModeloFuncionario extends IModelo<Funcionario> {

    // Atualiza o salário de todos os funcionários com base no parâmetro porcentagem
    List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException;

    // Retorna a soma dos salários de todos os funcionários
    BigDecimal retornarTotalSalarios() throws SQLException;

    // Retorna um Map onde as chaves são funcionários e os valores são a quantidade de salários
    // mínimos que recebem, considerando como salário mínimo o valor do parâmetro salarioMinimo
    Map<Funcionario, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException;

    // Retorna somente os funcionários que fazem aniversário em um dos meses especificados
    List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException;

    // Retorna os registros em ordem crescente (listarPorDataNascimento(Ordem.ASC))
    // ou decrescente (listarPorDataNascimento(Ordem.DESC)) ordenados pela data de nascimento
    List<Funcionario> listarPorDataNascimento(Ordem order) throws SQLException;
    default List<Funcionario> listarPorDataNascimento() throws SQLException {
        return listarPorDataNascimento(Ordem.ASC);
    }

    default Funcionario retornarFuncionarioMaisVelho() throws SQLException {
        // A menor data de nascimento corresponde ao funcionário de maior idade, portanto
        // somente precisamos extrair o primeiro funcionário da lista ordenada por data de nascimento
        return listarPorDataNascimento().get(0);
    }

    // Retorna uma List de Strings onde o indice 0 está associado ao nome do funcionário mais velho
    // e o indice 1 está associado à idade deste funcionário.
    default List<String> retornarNomeIdadeFuncionarioMaisVelho() throws SQLException {
        var funcionarioMaisVelho = retornarFuncionarioMaisVelho();
        var nome = funcionarioMaisVelho.getNome();
        var idade = calculaIdadePorDataNascimento(
                funcionarioMaisVelho.getDataNascimento());
        return List.of(nome, Integer.toString(idade));
    }

    // Retorna um Map onde as chaves são os nomes das funções dos funcionários cadastrados e os
    // valores são Lists que contém todos os funcionários que compartilham uma função em comum
    default Map<String, List<Funcionario>> agruparPorFuncao() throws SQLException {
        Map<String, List<Funcionario>> grupos = new HashMap<>();
        for (Funcionario funcionario : listar()) {
            var funcao = funcionario.getFuncao();
            if (!grupos.containsKey(funcao)) {
                grupos.put(funcao, new ArrayList<>());
            }
            grupos.get(funcao).add(funcionario);
        }
        return grupos;
    }

    // Método estático para calcular a idade de um funcionário com base na data atual do sistema e
    // na data de nascimento do funcionário
    static int calculaIdadePorDataNascimento(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
