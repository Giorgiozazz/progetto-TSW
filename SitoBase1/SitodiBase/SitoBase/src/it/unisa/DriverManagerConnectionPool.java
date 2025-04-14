package it.unisa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {

    private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Errore: Driver MySQL non trovato! " + e.getMessage());
        }
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        String ip = "localhost";
        String port = "3306";
        String dbName = "catalogo_auto";  // Nome del database (modificalo se necessario)
        String username = "root";
        String password = "root";

        Connection newConnection = DriverManager.getConnection(
                "jdbc:mysql://" + ip + ":" + port + "/" + dbName +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                        "&useLegacyDatetimeCode=false&serverTimezone=UTC" +
                        "&useSSL=false&verifyServerCertificate=false",
                username,
                password
        );
        newConnection.setAutoCommit(false);
        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            connection = freeDbConnections.remove(0);
            try {
                if (connection.isClosed()) {
                    connection = createDBConnection();
                }
            } catch (SQLException e) {
                connection = createDBConnection();
            }
        } else {
            connection = createDBConnection();
        }

        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            freeDbConnections.add(connection);
        }
    }
}
