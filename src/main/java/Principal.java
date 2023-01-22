import model.Conector;
import model.Modelo;
import model.entities.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Principal {
    private static final DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
            var modelo = new Modelo(conexao);

            insereFuncionarios(modelo);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }
    public static void insereFuncionarios(Modelo modelo) throws SQLException {
        modelo.criaFuncionario("Maria", LocalDate.parse("18/10/2000", formatHora),
                BigDecimal.valueOf(2009.44), "Operador");
        modelo.criaFuncionario("João", LocalDate.parse("12/05/1990", formatHora),
                BigDecimal.valueOf(2284.38), "Operador");
        modelo.criaFuncionario("Caio", LocalDate.parse("02/05/1961", formatHora),
                BigDecimal.valueOf(9836.14), "Coordenador");
        modelo.criaFuncionario("Miguel", LocalDate.parse("14/10/1988", formatHora),
                BigDecimal.valueOf(19119.88), "Diretor");
        modelo.criaFuncionario("Alice", LocalDate.parse("05/01/1995", formatHora),
                BigDecimal.valueOf(2234.68), "Recepcionista");
        modelo.criaFuncionario("Heitor", LocalDate.parse("19/11/1999", formatHora),
                BigDecimal.valueOf(1582.72), "Operador");
        modelo.criaFuncionario("Arthur", LocalDate.parse("31/03/1993", formatHora),
                BigDecimal.valueOf(4071.84), "Contador");
        modelo.criaFuncionario("Laura", LocalDate.parse("08/07/1994", formatHora),
                BigDecimal.valueOf(3017.45), "Gerente");
        modelo.criaFuncionario("Heloísa", LocalDate.parse("24/05/2003", formatHora),
                BigDecimal.valueOf(1606.85), "Eletricista");
        modelo.criaFuncionario("Helena", LocalDate.parse("02/09/1996", formatHora),
                BigDecimal.valueOf(2799.93), "Gerente");
    }
}
