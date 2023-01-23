package view;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class VisualizadorFuncionario extends Visualizador {
    public VisualizadorFuncionario(DateTimeFormatter formatData, DecimalFormat formatSalario) {
        super(formatData, formatSalario);
    }
    public VisualizadorFuncionario() {
        super();
    }
    public void cadastraFuncionario(Funcionario funcionario) {
        System.out.println("Funcionario(a) " + funcionario.getNome() + " cadastrado(a) com sucesso!");
    }
    public void deletaFuncionarioPorNome(String nome) {
        System.out.println("Funcionario(a) " + nome + " deletado(a) com sucesso!");
    }
    public void listaFuncionarios(List<Funcionario> funcionarios) {
        System.out.println(
                "\n################################ Lista de Funcionarios ##################################");
        var colunas = criaStringEspacada(
                "Nome", "Data de nascimento", "Salario", "Função");
        System.out.println(colunas);
        for (Funcionario funcionario : funcionarios) {
            var linha = criaStringEspacadaDeFuncionario(funcionario);
            System.out.println(linha);
        }
        System.out.println(
                "##########################################################################################\n");
    }
    public void atualizaFuncionario(Funcionario antigoFuncionario, Funcionario novoFuncionario) {
        System.out.println("Funcionário(a) " + antigoFuncionario.getNome() + "atualizado(a) com sucesso!");
        System.out.println("Novos valores: " + novoFuncionario.toString());
    }
    public void atualizaFuncionarios(List<Funcionario> antigosFuncionarios, List<Funcionario> novosFuncionarios) {
        for (int i=0; i<antigosFuncionarios.size(); i++) {
            atualizaFuncionario(antigosFuncionarios.get(i), novosFuncionarios.get(i));
        }
    }
    public void atualizaSalarioDeTodos(List<Funcionario> funcionarios, double porcentagem) {
        System.out.println("Salário de todos os funcionários atualizado em " + porcentagem + "%!");
        System.out.println("Exibindo novos valores...");
        listaFuncionarios(funcionarios);
    }

    public void agrupaFuncionariosPorFuncao(Map<String, List<Funcionario>> grupos) {
        System.out.println("Exibindo funcionários agrupados por função...");
        grupos.forEach((funcao, funcionarios) -> {
            System.out.println("Função: " + funcao);
            funcionarios.forEach(
                    funcionario -> System.out.println(criaStringEspacadaDeFuncionario(funcionario)));
        });
    }
    public String criaStringEspacadaDeFuncionario(Funcionario funcionario) {
        var nome = funcionario.getNome();
        var dataNascimento = formataData(funcionario.getDataNascimento());
        var salario = formataSalario(funcionario.getSalario());
        var funcao = funcionario.getFuncao();
        return criaStringEspacada(nome, dataNascimento, salario, funcao);
    }

    public void listaFuncionariosPorNome(List<Funcionario> funcionarios) {
        System.out.println("Exibindo funcionários em ordem alfabética...");
        listaFuncionarios(funcionarios);
    }
    public void retornaTotalSalarios(BigDecimal total) {
        System.out.println("Exibindo o salário total de todos os funcionários...");
        System.out.println("Total: " + "R$" + formataSalario(total));
    }
}
