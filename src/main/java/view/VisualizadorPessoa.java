package view;

import model.entities.Pessoa;

import java.util.List;

public class VisualizadorPessoa extends VisualizadorCMD<Pessoa> {
    @Override
    public void cadastrar(Pessoa pessoa) {
        System.out.println("Pessoa " + pessoa.getNome() + "cadastrada com sucesso!");
    }
    @Override
    public void deletarPorNome(String nome) {
        System.out.println("Pessoa " + nome + " deletada com sucesso!");
    }
    @Override
    public void listar(List<Pessoa> pessoas) {
        System.out.println(
                "\n########################## Lista de Pessoas ############################");
        var colunas = Formatador.criarStringEspacada(
                "Nome", "Data de nascimento");
        System.out.println(colunas);
        for (Pessoa pessoa : pessoas) {
            var nome = pessoa.getNome();
            var dataNascimento = getFormatador().formatarData(pessoa.getDataNascimento());
            var linha = Formatador.criarStringEspacada(nome, dataNascimento);
            System.out.println(linha);
        }
        System.out.println(
                "##############################################################################\n");
    }
    @Override
    public void listarPorNome(List<Pessoa> entidades) {
        System.out.println("Exibindo funcionários em ordem alfabética...");
        listar(entidades);
    }
    @Override
    public void atualizar(Pessoa entidadeAntiga, Pessoa entidadeNova) {
        System.out.println("Pessoa(a) " + entidadeAntiga.getNome() + "atualizado(a) com sucesso!");
        System.out.println("Novos valores: " + entidadeNova.toString() + "\n");
    }
}
