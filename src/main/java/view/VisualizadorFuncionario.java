package view;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VisualizadorFuncionario extends VisualizadorCMD<Funcionario>
        implements IVisualizadorFuncionario {
    public VisualizadorFuncionario(Formatador formatador) {
        super(formatador);
    }
    public VisualizadorFuncionario() {
        super();
    }
    @Override
    public void cadastrar(Funcionario funcionario) {
        System.out.println("Funcionario(a) " + funcionario.getNome() + " cadastrado(a) com sucesso!\n");
    }
    @Override
    public void deletarPorNome(String nome) {
        System.out.println("Funcionario(a) " + nome + " deletado(a) com sucesso!\n");
    }
    @Override
    public void listar(List<Funcionario> funcionarios) {
        System.out.println(
                "################################ Lista de Funcionarios ##################################");
        var colunas = Formatador.criarStringEspacada(
                "Nome", "Data de nascimento", "Salario", "Função");
        System.out.println(colunas);
        for (Funcionario funcionario : funcionarios) {
            var linha = criarStringEspacadaDeFuncionario(funcionario);
            System.out.println(linha);
        }
        System.out.println(
                "##########################################################################################\n");
    }
    @Override
    public void atualizar(Funcionario antigoFuncionario, Funcionario novoFuncionario) {
        System.out.println("Funcionário(a) " + antigoFuncionario.getNome() + "atualizado(a) com sucesso!");
        System.out.println("Novos valores: " + novoFuncionario.toString() + "\n");
    }
    @Override
    public void listarPorNome(List<Funcionario> funcionarios) {
        System.out.println("Exibindo funcionários em ordem alfabética...");
        listar(funcionarios);
    }
    @Override
    public void atualizarSalarioDeTodos(List<Funcionario> funcionarios, double porcentagem) {
        System.out.println("Salário de todos os funcionários atualizado em " + porcentagem + "%!");
        System.out.println("Exibindo novos valores...");
        listar(funcionarios);
    }
    @Override
    public void agruparPorFuncao(Map<String, List<Funcionario>> grupos) {
        System.out.println("Exibindo funcionários agrupados por função...");
        grupos.forEach((funcao, funcionarios) -> {
            System.out.println("Função: " + funcao);
            funcionarios.forEach(
                    funcionario -> System.out.println(criarStringEspacadaDeFuncionario(funcionario)));
        });
        System.out.println();
    }
    @Override
    public void retornarTotalSalarios(BigDecimal total) {
        System.out.println("Exibindo o salário total de todos os funcionários...");
        System.out.println("Total: " + "R$" + getFormatador().formatarDecimal(total) + "\n");
    }
    @Override
    public void listarComSalariosMinimos(double salarioMinimo, Map<Funcionario, BigDecimal> relacao) {
        System.out.println("Exibindo quantos salários mínimos ganha cada funcionário...");
        System.out.println("Valor considerado do salário mínimo: R$" + salarioMinimo);

        // Exibe o nome das colunas de forma a ficar alinhada com os dados das linhas
        var colunas = Formatador.criarStringEspacada("Nome", "Salário");
        System.out.println(colunas);

        relacao.forEach((funcionario, salario) -> {
            // Exibe os dados da linha, formatando o salário com base o formatador
            var linha = Formatador.criarStringEspacada(funcionario.getNome(),
                    getFormatador().formatarDecimal(salario));
            System.out.println(linha);
        });
        System.out.println();
    }
    @Override
    public void listarAniversariantesDosMeses(List<Funcionario> aniversariantes, int ... meses) {
        StringBuilder mesesConsiderados = new StringBuilder();
        for (int i=0; i<meses.length; i++) {
            // Constrói uma string para exibir os meses considerados separando-os por vírgula
            // Ex: "2, 6" ou "10, 12, 1"
            mesesConsiderados.append(meses[i]).append((i == meses.length - 1)? "" : ", ");
        }
        System.out.println("Exibindo os aniversariantes dos meses: " + mesesConsiderados.toString());
        listar(aniversariantes);
    }
    @Override
    public void retornarNomeIdadeFuncionarioMaisVelho(List<String> dados) {
        System.out.println("Exibindo o nome e idade do funcionário mais velho...");
        System.out.println("Nome: " + dados.get(0));
        System.out.println("Idade: " + dados.get(1));
        System.out.println();
    }
    public String criarStringEspacadaDeFuncionario(Funcionario funcionario) {
        //Cria uma string espaçada com todos os dados de um funcionário exceto o id
        var nome = funcionario.getNome();
        var dataNascimento = getFormatador().formatarData(funcionario.getDataNascimento());
        var salario = getFormatador().formatarDecimal(funcionario.getSalario());
        var funcao = funcionario.getFuncao();
        return Formatador.criarStringEspacada(nome, dataNascimento, salario, funcao);
    }
}
