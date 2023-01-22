package model;

import java.sql.*;

public class Conector {
    public static Connection conectar(String url, String username, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(Connection conexao = DriverManager.getConnection(url, username, password)) {
            conexao.setAutoCommit(true);
            return conexao;
        }
    }
}
