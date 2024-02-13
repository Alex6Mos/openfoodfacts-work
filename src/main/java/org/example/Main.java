package org.example;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.database.DatabaseConfig;
import org.example.database.DatabaseInitializer;
import org.example.database.DatabaseManager;
import org.example.models.*;

import static org.apache.spark.sql.functions.*;
import static org.example.database.DatabaseManager.*;


public class Main {

    public static void main(String[] args) throws SQLException {
        SparkSession spark = SparkSession.builder().appName("AlimentsCompatibles").master("local[3]").getOrCreate();
        Connection connection = DatabaseConfig.connectToPostgreSQL();

        // Initialisation des tables de la base de données
        DatabaseInitializer.initializeTables(connection);
        initialiserEtEnregistrerJours(spark);
        initialiserEtEnregistrerRegimes(spark);


        Dataset<Row> dataCleaned = DataLoader.loadDataAndClean(spark);

        // Processus de traitement des données
        DataManager.processData(spark, connection, dataCleaned);

        // Fermeture de la session Spark
        spark.stop();
    }

}
