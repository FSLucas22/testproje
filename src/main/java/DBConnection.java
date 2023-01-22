import java.sql.*;

public class DBConnection {
    private final Connection connection;

    public DBConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection connect(String url, String username, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            return new DBConnection(connection);
        }
    }
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
    public void executeUpdate(String updateCommand) {
        // TODO
    }
}
