package model;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// Prova de conceito do Padrão MVC adotado:
// Como essa classe também implementa a interface IModeloFuncionario
// é possível utilizá-la no lugar de ModeloFuncionário sem alterar o restante do código, somente
// injetando-o no controlador.
// Também pode ser usado para mockar o banco de dados.

public class MockModeloFuncionario implements IModeloFuncionario {
    private final List<Funcionario> mockConexao = new ArrayList<>(); // Representa o banco de dados
    private int lastId = 0; // Usado para simular a geração automática de ids

    private List<Funcionario> copiarResultado(List<Funcionario> resultado) {
        // Copia of funcionários do resultado e os salva em uma nova lista, para que os funcionários
        // do resultado não sejam afetados por eventuais mudanças no estado dos objetos retornados
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
        // Para cada funcionário cadastrado
        mockConexao.forEach(funcionario -> {
            // aumenta o salário do funcionário em porcentagem%
            var novoSalario = funcionario.getSalario().multiply(
                    BigDecimal.valueOf(1 + porcentagem / 100));
            funcionario.setSalario(novoSalario);
        });
        // Retorna os funcionários com os salários atualizados
        return listar();
    }
    @Override
    public BigDecimal retornarTotalSalarios() throws SQLException {
        return mockConexao.stream()
                .map(Funcionario::getSalario) // Gera uma stream com os salários dos funcionários cadastrados
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os salários e retorna o total
    }
    @Override
    public Map<Funcionario, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException {
        return mockConexao.stream().collect(
                // Cria o Map que associa o nome do funcionário à quantidade de salários mínimos que
                // recebe
                Collectors.toMap(
                        Function.identity(),
                        // Calcula quantos salários mínimos compoem o salário, com precisão de 2 casas
                        // decimais após a virgula
                        f -> f.getSalario().divide(BigDecimal.valueOf(salarioMinimo),
                                2, RoundingMode.HALF_EVEN),
                        (antigo, novo) -> antigo
                )
        );
    }
    @Override
    public List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException {
        return mockConexao.stream().filter(
                // Extrai o número do mês e verifica se está contido na Array meses
                f -> Arrays.stream(meses).anyMatch(i -> i == f.getDataNascimento().getMonthValue())
        ).collect(Collectors.toList());
    }
    @Override
    public List<Funcionario> listarPorDataNascimento(Ordem ordem) throws SQLException {
        var funcionarios = listar();
        // Ordena em ordem ascendente
        if (ordem == Ordem.ASC) {
            funcionarios.sort(
                    Comparator.comparing(Pessoa::getDataNascimento)
            );
        // Ordena em ordem decrescente
        } else {
            funcionarios.sort(Collections.reverseOrder(Comparator.comparing(Pessoa::getDataNascimento)));
        }
        return funcionarios;
    }

    @Override
    public Funcionario cadastrar(Funcionario entidade) throws SQLException {
        var id = ++lastId;

        // Cria um novo funcionário, com dados iguais aos de entidade
        var novoFuncionario = copiarResultado(List.of(entidade)).get(0);

        novoFuncionario.setId(id);
        mockConexao.add(novoFuncionario);
        return novoFuncionario;
    }

    @Override
    public void deletarPorNome(String nome) throws SQLException {
        // Deleta todos os funcionários que tenham o nome passado no parâmetro
        int i = 0;
        while (i < mockConexao.size()) {
            var funcionario = mockConexao.get(i);
            if (funcionario.getNome().equals(nome)) {
                mockConexao.remove(funcionario);
            }
            i++;
        }
    }

    @Override
    public List<Funcionario> listar() throws SQLException {
        // chamada ao método copiarResultado é necessária para que mudanças na
        // lista retornada não afetem a lista mockConexao
        return copiarResultado(mockConexao);
    }

    @Override
    public List<Funcionario> listarPorNome(Ordem ordem) throws SQLException {
        var funcionarios = listar();
        // Ordena em ordem ascendente
        if (ordem == Ordem.ASC) {
            funcionarios.sort(
                    Comparator.comparing(Pessoa::getNome)
            );
        // Ordena em ordem decrescente
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
