import controller.ControladorFuncionario;
import model.Conector;
import model.ModeloFuncionario;
import model.entities.Funcionario;
import view.VisualizadorFuncionario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Principal {
    private static final DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
            var visualizador = new VisualizadorFuncionario();
            var controlador = new ControladorFuncionario(modelo, visualizador);

            // Insere os funcionários na ordem da tabela
            insereFuncionarios(controlador);

            // Deleta o funcionário João
            modelo.deletaFuncionarioPorNome("João");

            // Mostra todas as informações de todos os funcionários
            controlador.listaFuncionarios();

            // Atualiza o salário de todos os funcionários em 10% e mostra a lista com os novos salários
            controlador.atualizaSalarioDeTodos(10);

            // Exibe os funcionários agrupados por função
            controlador.agrupaFuncionariosPorFuncao();

            // @TODO 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.

            // @TODO 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.

            // Exibe os funcionários em ordem alfabética
            controlador.listaFuncionariosPorNome();

            // Exibe o total dos salários dos funcionários
            controlador.retornaTotalSalarios();

            // Imprime quantos salários mínimos ganha cada funcionário
            // considerando que o salário mínimo é R$1212.00
            controlador.salariosMinimos(1212);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }
    public static void insereFuncionarios(ControladorFuncionario controlador) throws SQLException {
        controlador.cadastraFuncionario("Maria", LocalDate.parse("18/10/2000", formatData),
                BigDecimal.valueOf(2009.44), "Operador");
        controlador.cadastraFuncionario("João", LocalDate.parse("12/05/1990", formatData),
                BigDecimal.valueOf(2284.38), "Operador");
        controlador.cadastraFuncionario("Caio", LocalDate.parse("02/05/1961", formatData),
                BigDecimal.valueOf(9836.14), "Coordenador");
        controlador.cadastraFuncionario("Miguel", LocalDate.parse("14/10/1988", formatData),
                BigDecimal.valueOf(19119.88), "Diretor");
        controlador.cadastraFuncionario("Alice", LocalDate.parse("05/01/1995", formatData),
                BigDecimal.valueOf(2234.68), "Recepcionista");
        controlador.cadastraFuncionario("Heitor", LocalDate.parse("19/11/1999", formatData),
                BigDecimal.valueOf(1582.72), "Operador");
        controlador.cadastraFuncionario("Arthur", LocalDate.parse("31/03/1993", formatData),
                BigDecimal.valueOf(4071.84), "Contador");
        controlador.cadastraFuncionario("Laura", LocalDate.parse("08/07/1994", formatData),
                BigDecimal.valueOf(3017.45), "Gerente");
        controlador.cadastraFuncionario("Heloísa", LocalDate.parse("24/05/2003", formatData),
                BigDecimal.valueOf(1606.85), "Eletricista");
        controlador.cadastraFuncionario("Helena", LocalDate.parse("02/09/1996", formatData),
                BigDecimal.valueOf(2799.93), "Gerente");
    }
}
