import model.Conector;
import model.Modelo;
import model.entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Principal {
    private static final DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String ... args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String nomeUsuario = "testuser";
        String senha = "12345";
        try (Connection conexao = Conector.conectar(
                jdbcUrl,
                nomeUsuario,
                senha
        )){
            var modelo = new Modelo(conexao);
            var pessoa = modelo.criaPessoa("Teste", LocalDate.parse("22/06/1999", formatHora));
            System.out.println(pessoa);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }
}
