package org.example.database;

import org.apache.spark.sql.SparkSession;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initializeTables(Connection connection) {
        try {
            DatabaseManager.createRegimeTable(connection);
            DatabaseManager.createUtilisateursTable(connection);
            DatabaseManager.createJoursTable(connection);
            DatabaseManager.createRepasTable(connection);
            DatabaseManager.createMenusTable(connection);
            DatabaseManager.createUtilisateursRepasTable(connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

