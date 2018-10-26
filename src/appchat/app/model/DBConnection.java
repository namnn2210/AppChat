package appchat.app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection = null;
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private final String DATABASE = "app_chat_db";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String UTF8_URL = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static DBConnection instance;

    public static DBConnection getInstance() {
        if(instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(CONNECTION_URL+DATABASE+UTF8_URL,USERNAME,PASSWORD);
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException sqlEx) {
            System.err.println(sqlEx.getMessage());
        }
    }
}
