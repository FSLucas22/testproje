/*
    Para rodar a aplicação é necessário antes subir o banco de dados com a tabela
    Funcionario (Script SQL para geração da tabela em src/main/cria_tabela_funcionario.sql).
    Também é necessário colocar a url do driver jdbc, o nome de usuário e a senha nas variáveis abaixo
    para estabelecer a conexão com o banco.
    Em caso de impossibilidade de conexão, é possível remover do código a tentativa de conexão e utilizar o
    objeto MockModeloFuncionario para testar a aplicação, ao inves de utilizar o ModeloFuncionario.
 */


import controller.ControladorFuncionario;
import model.Conector;
import model.MockModeloFuncionario;
import model.ModeloFuncionario;
import model.Ordem;
import model.entities.Funcionario;
import view.VisualizadorFuncionario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Principal {
    private static final DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Necessário adaptar com as informações do banco
    private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String nomeUsuario = "testuser";
    private static final String senha = "12345";
    public static void main(String ... args) {
        // Estabelece a conexão com o banco de dados
        try (Connection conexao = Conector.conectar(
                jdbcUrl,
                nomeUsuario,
                senha
        )){
            // Cria o controlador para lidar com o banco
            var modelo = new ModeloFuncionario(conexao);
            // var modelo = new MockModeloFuncionario();
            var visualizador = new VisualizadorFuncionario();
            var controlador = new ControladorFuncionario(modelo, visualizador);

            // Insere os funcionários na ordem da tabela
            insereFuncionarios(controlador);

            // Deleta o funcionário João
            modelo.deletarPorNome("João");

            // Mostra todas as informações de todos os funcionários
            controlador.listar();

            // Atualiza o salário de todos os funcionários em 10% e mostra a lista com os novos salários
            controlador.atualizarSalarioDeTodos(10);

            // Exibe os funcionários agrupados por função,
            // retornando um Map que agrupa os funcionários por função
            controlador.agruparPorFuncao();

            // Exibe os funcionários que fazem aniversário nos meses 10 e 12
            controlador.listarAniversariantesDosMeses(10, 12);

            // Imprime o funcionário com a maior idade, exibindo os atributos: nome e idade
            controlador.retornarNomeIdadeFuncionarioMaisVelho();

            // Exibe os funcionários em ordem alfabética
            controlador.listarPorNome();

            // Exibe o total dos salários dos funcionários
            controlador.retornarTotalSalarios();

            // Imprime quantos salários mínimos ganha cada funcionário
            // considerando que o salário mínimo é R$1212.00
            controlador.listarComSalariosMinimos(1212);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }

    public static void insereFuncionarios(ControladorFuncionario controlador) throws SQLException {
        // Método para realizar a carga inicial de dados
        controlador.cadastrar(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatData),
                BigDecimal.valueOf(2009.44), "Operador"));
        controlador.cadastrar(new Funcionario("João", LocalDate.parse("12/05/1990", formatData),
                BigDecimal.valueOf(2284.38), "Operador"));
        controlador.cadastrar(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatData),
                BigDecimal.valueOf(9836.14), "Coordenador"));
        controlador.cadastrar(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatData),
                BigDecimal.valueOf(19119.88), "Diretor"));
        controlador.cadastrar(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatData),
                BigDecimal.valueOf(2234.68), "Recepcionista"));
        controlador.cadastrar(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatData),
                BigDecimal.valueOf(1582.72), "Operador"));
        controlador.cadastrar(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatData),
                BigDecimal.valueOf(4071.84), "Contador"));
        controlador.cadastrar(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatData),
                BigDecimal.valueOf(3017.45), "Gerente"));
        controlador.cadastrar(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatData),
                BigDecimal.valueOf(1606.85), "Eletricista"));
        controlador.cadastrar(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatData),
                BigDecimal.valueOf(2799.93), "Gerente"));
    }
}
