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
            // Cria o model para lidar com o banco
            var modelo = new ModeloFuncionario(conexao);
            var visualizador = new VisualizadorFuncionario();
            insereFuncionarios(modelo);

            // Deleta o funcionário João
            modelo.deletaFuncionarioPorNome("João");

            // Mostra todas as informações de todos os funcionários
            visualizador.listaFuncionarios(modelo.listaFuncionarios());

            // Atualiza o salário de todos os funcionários em 10%
            atualizaSalarioDeTodos(modelo);

            // Mostra a lista com os salários atualizados
            visualizador.listaFuncionarios(modelo.listaFuncionarios());

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }
    public static void insereFuncionarios(ModeloFuncionario modelo) throws SQLException {
        modelo.cadastraFuncionario("Maria", LocalDate.parse("18/10/2000", formatData),
                BigDecimal.valueOf(2009.44), "Operador");
        modelo.cadastraFuncionario("João", LocalDate.parse("12/05/1990", formatData),
                BigDecimal.valueOf(2284.38), "Operador");
        modelo.cadastraFuncionario("Caio", LocalDate.parse("02/05/1961", formatData),
                BigDecimal.valueOf(9836.14), "Coordenador");
        modelo.cadastraFuncionario("Miguel", LocalDate.parse("14/10/1988", formatData),
                BigDecimal.valueOf(19119.88), "Diretor");
        modelo.cadastraFuncionario("Alice", LocalDate.parse("05/01/1995", formatData),
                BigDecimal.valueOf(2234.68), "Recepcionista");
        modelo.cadastraFuncionario("Heitor", LocalDate.parse("19/11/1999", formatData),
                BigDecimal.valueOf(1582.72), "Operador");
        modelo.cadastraFuncionario("Arthur", LocalDate.parse("31/03/1993", formatData),
                BigDecimal.valueOf(4071.84), "Contador");
        modelo.cadastraFuncionario("Laura", LocalDate.parse("08/07/1994", formatData),
                BigDecimal.valueOf(3017.45), "Gerente");
        modelo.cadastraFuncionario("Heloísa", LocalDate.parse("24/05/2003", formatData),
                BigDecimal.valueOf(1606.85), "Eletricista");
        modelo.cadastraFuncionario("Helena", LocalDate.parse("02/09/1996", formatData),
                BigDecimal.valueOf(2799.93), "Gerente");
    }
    public static BigDecimal calculaSalarioComAumento(BigDecimal salario, double porcentagemAumento) {
        return salario.add(salario.multiply(new BigDecimal(porcentagemAumento / 100)));
    }
    public static void atualizaSalarioDeTodos(ModeloFuncionario modelo) throws SQLException {
        var funcionarios = modelo.listaFuncionarios();
        for (Funcionario funcionario : funcionarios) {
            var novoSalario = calculaSalarioComAumento(funcionario.getSalario(), 10);
            funcionario.setSalario(novoSalario);
        }
        modelo.atualizaFuncionarios(funcionarios);
    }
}
