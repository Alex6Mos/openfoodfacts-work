package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/openfoodfacts";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "postgres";

    public static Connection connectToPostgreSQL() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
