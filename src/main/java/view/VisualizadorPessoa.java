package view;

import model.entities.Funcionario;
import model.entities.Pessoa;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisualizadorPessoa extends Visualizador {
    public VisualizadorPessoa(DateTimeFormatter formatData, DecimalFormat formatSalario) {
        super(formatData, formatSalario);
    }
    public VisualizadorPessoa() {
        super();
    }
    public void cadastraPessoa(Pessoa pessoa) {
        System.out.println("Pessoa " + pessoa.getNome() + "cadastrada com sucesso!");
    }
    public void deletaPessoaPorNome(String nome) {
        System.out.println("Funcionario " + nome + " deletado com sucesso!");
    }
    public void listaPessoas(List<Pessoa> pessoas) {
        System.out.println(
                "\n########################## Lista de Pessoas ############################");
        var colunas = criaStringEspacada(
                "Nome", "Data de nascimento");
        System.out.println(colunas);
        for (Pessoa pessoa : pessoas) {
            var nome = pessoa.getNome();
            var dataNascimento = formataData(pessoa.getDataNascimento());
            var linha = criaStringEspacada(nome, dataNascimento);
            System.out.println(linha);
        }
        System.out.println(
                "##############################################################################\n");
    }
}
