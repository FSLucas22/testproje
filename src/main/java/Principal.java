import java.sql.SQLException;

public class Principal {
    public static void main(String ... args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
        String nomeUsuario = "testuser";
        String senha = "12345";
        try {
            DBConnection dbconnection = DBConnection.connect(
                    jdbcUrl,
                    nomeUsuario,
                    senha
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Um erro ocorreu: ");
            System.out.println(e.getMessage());
        }
    }
}
