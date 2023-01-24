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


public interface IModeloFuncionario extends Modelo<Funcionario> {
    List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException;
    BigDecimal retornarTotalSalarios() throws SQLException;
    Map<String, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException;
    List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException;
    List<Funcionario> listarFuncionariosPorDataNascimento(Ordem order) throws SQLException;
    default List<Funcionario> listarFuncionariosPorDataNascimento() throws SQLException {
        return listarFuncionariosPorDataNascimento(Ordem.ASC);
    }
    default Funcionario retornarFuncionarioMaisVelho() throws SQLException {
        return listarFuncionariosPorDataNascimento().get(0);
    }
    default List<String> retornarNomeIdadeFuncionarioMaisVelho() throws SQLException {
        var funcionarioMaisVelho = retornarFuncionarioMaisVelho();
        var nome = funcionarioMaisVelho.getNome();
        var idade = calculaIdadePorDataNascimento(
                funcionarioMaisVelho.getDataNascimento());
        return List.of(nome, Integer.toString(idade));
    }
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
    static int calculaIdadePorDataNascimento(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
