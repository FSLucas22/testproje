package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MockModeloFuncionario implements IModeloFuncionario {
    private final List<Funcionario> mockConexao = new ArrayList<>();
    private int lastId = 0;

    private List<Funcionario> copiarResultado(List<Funcionario> resultado) {
        List<Funcionario> copia = new ArrayList<>();
        for (Funcionario funcionario : resultado) {
            var copiaFuncionario = new Funcionario(
                    funcionario.getId(),
                    funcionario.getNome(),
                    funcionario.getDataNascimento(),
                    funcionario.getSalario(),
                    funcionario.getFuncao()
            );
            copia.add(copiaFuncionario);
        }
        return copia;
    }
    @Override
    public List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException {
        mockConexao.forEach(funcionario -> {
            var novoSalario = funcionario.getSalario().multiply(
                    BigDecimal.valueOf(1 + porcentagem / 100));
            funcionario.setSalario(novoSalario);
        });
        return listar();
    }
    @Override
    public BigDecimal retornarTotalSalarios() throws SQLException {
        return mockConexao.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public Map<String, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException {
        return mockConexao.stream().collect(
                Collectors.toMap(
                        Funcionario::getNome,
                        f -> f.getSalario().divide(BigDecimal.valueOf(salarioMinimo),
                                new MathContext(2, RoundingMode.HALF_EVEN)),
                        (antigo, novo) -> antigo
                )
        );
    }
    @Override
    public List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException {
        return mockConexao.stream().filter(
                f -> Arrays.stream(meses).anyMatch(i -> i == f.getDataNascimento().getMonthValue())
        ).collect(Collectors.toList());
    }
    @Override
    public List<Funcionario> listarFuncionariosPorDataNascimento(Ordem ordem) throws SQLException {
        var funcionarios = listar();
        if (ordem == Ordem.ASC) {
            funcionarios.sort(
                    Comparator.comparing(Pessoa::getDataNascimento)
            );
        } else {
            funcionarios.sort(Collections.reverseOrder(Comparator.comparing(Pessoa::getDataNascimento)));
        }
        return funcionarios;
    }

    @Override
    public Funcionario cadastrar(Funcionario entidade) throws SQLException {
        var id = ++lastId;
        var novoFuncionario = copiarResultado(List.of(entidade)).get(0);
        novoFuncionario.setId(id);
        mockConexao.add(novoFuncionario);
        return novoFuncionario;
    }

    @Override
    public void deletarPorNome(String nome) throws SQLException {
        for (Funcionario funcionario : mockConexao) {
            if (funcionario.getNome().equals(nome)) {
                mockConexao.remove(funcionario);
                return;
            }
        }
    }

    @Override
    public List<Funcionario> listar() throws SQLException {
        return copiarResultado(mockConexao);
    }

    @Override
    public List<Funcionario> listarPorNome(Ordem ordem) throws SQLException {
        var funcionarios = listar();
        if (ordem == Ordem.ASC) {
            funcionarios.sort(
                    Comparator.comparing(Pessoa::getNome)
            );
        } else {
            funcionarios.sort(Collections.reverseOrder(Comparator.comparing(Pessoa::getNome)));
        }
        return funcionarios;
    }

    @Override
    public Funcionario retornarPorId(int id) throws SQLException {
        var resultado = mockConexao.stream().filter(f -> f.getId() == id).toList();
        return copiarResultado(resultado).get(0);
    }

    @Override
    public Funcionario atualizar(Funcionario entidade) throws SQLException {
         for (Funcionario funcionario : mockConexao) {
             if (funcionario.getId() != entidade.getId()) {
                 continue;
             }
             funcionario.setNome(entidade.getNome());
             funcionario.setDataNascimento(entidade.getDataNascimento());
             funcionario.setSalario(entidade.getSalario());
             funcionario.setFuncao(entidade.getFuncao());
             break;
         }
        return retornarPorId(entidade.getId());
    }
}
