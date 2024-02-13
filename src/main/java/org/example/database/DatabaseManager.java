package org.example.database;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.example.DataframesFactory;
import org.example.RegimesFactory;
import org.example.UtilisateursFactory;
import org.example.models.*;

import java.sql.*;

import static org.apache.spark.sql.functions.lit;

public class DatabaseManager {

    private static final String JDBC_URL = DatabaseConfig.JDBC_URL;
    private static final String USERNAME = DatabaseConfig.USERNAME;
    private static final String PASSWORD = DatabaseConfig.PASSWORD;

    public static void createRegimeTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "regimes", new String[]{"TABLE"});

        if (!tables.next()) {
            // Création de la table uniquement si elle n'existe pas
            Statement statement = conn.createStatement();
            String createTableSQL = "CREATE TABLE regimes (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom_regime VARCHAR(100))";
            statement.execute(createTableSQL);

            System.out.println("Table régime créée avec succès.");
        }
    }

    public static void createUtilisateursTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "utilisateurs", new String[]{"TABLE"});

        if (!tables.next()) {
            // Création du type ENUM si elle n'existe pas
            Statement statement = conn.createStatement();
            String createEnumTypeSQL = "DO $$ BEGIN " +
                    "IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'enum_sexe') THEN " +
                    "CREATE TYPE enum_sexe AS ENUM ('Homme', 'Femme'); " +
                    "END IF; " +
                    "END $$;";
            statement.execute(createEnumTypeSQL);

            // Création de la table utilisateurs
            String createTableSQL = "CREATE TABLE utilisateurs (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom_utilisateur VARCHAR(100), " +
                    "taille INTEGER, " +
                    "sexe enum_sexe, " +
                    "poids FLOAT, " +
                    "imc FLOAT, " +
                    "regime_id INTEGER REFERENCES regimes(id) )";
            statement.execute(createTableSQL);

        }
    }

    public static void createUtilisateursRepasTable(Connection conn) throws SQLException {

        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "utilisateurs_repas", new String[]{"TABLE"});

        if(!tables.next()) {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE utilisateurs_repas (" +
                    "utilisateur_id INT, " +
                    "repa_id INT, " +
                    "PRIMARY KEY(utilisateur_id, repa_id), " +
                    "FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id), " +
                    "FOREIGN KEY (repa_id) REFERENCES repas(id)" +
                    ");";

            stmt.execute(sql);
            System.out.println("Table 'utilisateurs_repas' créée");

        }
    }

    public static void createJoursTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "jours", new String[]{"TABLE"});

        if (!tables.next()) {
            // Création de la table uniquement si elle n'existe pas
            Statement statement = conn.createStatement();
            String createTableSQL = "CREATE TABLE jours (" +
                    "id SERIAL PRIMARY KEY, " +
                    "jour_nom VARCHAR(50) )";
            statement.execute(createTableSQL);

            System.out.println("Table jours créée avec succès.");
        }
    }
    public static void createRepasTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "repas", new String[]{"TABLE"});

        if (!tables.next()) {
            // Création de la table uniquement si elle n'existe pas
            Statement statement = conn.createStatement();
            String createTableSQL = "CREATE TABLE repas (" +
                    "id SERIAL PRIMARY KEY, " +
                    "repa_periode VARCHAR(100), " +
                    "repa_aliments VARCHAR(255)[], " +
                    "FOREIGN KEY (id) REFERENCES jours(id) )";
            statement.execute(createTableSQL);

            System.out.println("Table 'repas' créée avec succès.");
        }
    }

    public static void createMenusTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet tables = meta.getTables(null, null, "menus", new String[]{"TABLE"});

        if (!tables.next()) {
            // Création de la table uniquement si elle n'existe pas
            Statement statement = conn.createStatement();
            String createTableSQL = "CREATE TABLE menus (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom_menu VARCHAR(50), "+
                    "utilisateur_id INTEGER REFERENCES utilisateurs(id), "+
                    "jour_id INTEGER REFERENCES jours(id) ) ";

            statement.execute(createTableSQL);

            System.out.println("Table 'menu' créée avec succès.");
        }
    }



    public static void initialiserEtEnregistrerJours(SparkSession spark) throws SQLException {
        String[] nomsDesJours = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};

        for (String nomJour : nomsDesJours) {
            Jour jour = new Jour(nomJour);
            saveJourToPostgreSQL(spark, jour);
        }
    }


    public static void initialiserEtEnregistrerRegimes(SparkSession spark) {
        Regime[] regimes = {
                RegimesFactory.createFodmapRegime(),
                RegimesFactory.createMediterraneanRegime(),
                RegimesFactory.createHypoglycemicRegime()
        };

        for (Regime regime : regimes) {
            saveRegimeToPostgreSQL(spark, regime);
        }
    }


    public static void creerEtEnregistrerUtilisateur(Connection connection, String nom, int taille, Sexe sexe, float poids, int regimeID) {
        Utilisateur utilisateur = UtilisateursFactory.createUtilisateur(nom, taille, sexe, poids, regimeID);
        saveUtilisateurToPostgreSQL(connection, utilisateur);
    }



    public static void saveRegimeToPostgreSQL(SparkSession spark, Regime regime) {
        System.out.println("Tentative sauvegarde regime...");
        Dataset<Row> regimeDF = DataframesFactory.createRegimeDataFrame(spark, regime);

        regimeDF.write()
                .format("jdbc")
                .option("url", JDBC_URL)
                .option("dbtable", "regimes")
                .option("user", USERNAME)
                .option("password", PASSWORD)
                .mode(SaveMode.Append)
                .save();

        System.out.println(regime.getName() + " sauvegarder avec succès !");
    }

    public static void saveUtilisateurToPostgreSQL(Connection conn, Utilisateur utilisateur) {
        String insertSQL = "INSERT INTO utilisateurs (nom_utilisateur, taille, sexe, poids, imc, regime_id) " +
                "VALUES (?, ?, ?::enum_sexe, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setInt(2, utilisateur.getTaille());
            pstmt.setString(3, utilisateur.getSexe().toString()); // Sexe est un enum, donc on utilise toString()
            pstmt.setFloat(4, utilisateur.getPoids());
            pstmt.setFloat(5, utilisateur.getImc());
            pstmt.setInt(6, utilisateur.getRegime());

            pstmt.executeUpdate();

            System.out.println("Données utilisateur " + utilisateur.getNom() + " enregistrées dans la base de données PostgreSQL.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveJourToPostgreSQL(SparkSession spark, Jour jour) {

        Dataset<Row> jourDF = DataframesFactory.createJourDataFrame(spark, jour);

        jourDF.write()
                .format("jdbc")
                .option("url", JDBC_URL)
                .option("dbtable", "jours")
                .option("user", USERNAME)
                .option("password", PASSWORD)
                .mode(SaveMode.Append)
                .save();

        System.out.println("Données jours " + jour.getName() + " enregistrées dans la base de données PostgreSQL.");

    }

    public static  void  saveUtilisateurRepaToPostgreSQL(SparkSession spark, UtilisateurRepa utilisateurRepa){
        Dataset<Row> utilisateurRepaDF = DataframesFactory.createUtilisateurRepaDataFrame(spark, utilisateurRepa);

        utilisateurRepaDF.write()
                .format("jdbc")
                .option("url", JDBC_URL)
                .option("dbtable", "utilisateurs_repas")
                .option("user", USERNAME)
                .option("password", PASSWORD)
                .mode(SaveMode.Append)
                .save();

    }


}
