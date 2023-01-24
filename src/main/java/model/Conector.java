package model;

import java.sql.*;

public class Conector {
    public static Connection conectar(String url, String username, String password)
            throws ClassNotFoundException, SQLException {
        // Estabelece a conexão com o banco
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conexao = DriverManager.getConnection(url, username, password);

        // Caso um erro ocorra durante a execução do programa, as mudanças não serão feitas no banco
        conexao.setAutoCommit(false);
        return conexao;
    }
}
