package view;

import model.entities.Funcionario;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisualizadorFuncionario extends Visualizador {
    public VisualizadorFuncionario(DateTimeFormatter formatData, DecimalFormat formatSalario) {
        super(formatData, formatSalario);
    }
    public VisualizadorFuncionario() {
        super();
    }
    public void cadastraFuncionario(Funcionario funcionario) {
        System.out.println("Funcionario " + funcionario.getNome() + "cadastrado com sucesso!");
    }
    public void deletaFuncionarioPorNome(String nome) {
        System.out.println("Funcionario " + nome + " deletado com sucesso!");
    }
    public void listaFuncionarios(List<Funcionario> funcionarios) {
        System.out.println(
                "\n################################ Lista de Funcionarios ##################################");
        var colunas = criaStringEspacada(
                "Nome", "Data de nascimento", "Salario", "Função");
        System.out.println(colunas);
        for (Funcionario funcionario : funcionarios) {
            var nome = funcionario.getNome();
            var dataNascimento = formataData(funcionario.getDataNascimento());
            var salario = formataSalario(funcionario.getSalario());
            var funcao = funcionario.getFuncao();
            var linha = criaStringEspacada(nome, dataNascimento, salario, funcao);
            System.out.println(linha);
        }
        System.out.println(
                "##########################################################################################\n");
    }
}
