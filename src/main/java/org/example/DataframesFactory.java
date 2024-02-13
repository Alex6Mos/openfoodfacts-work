package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.example.models.*;

import java.util.ArrayList;
import java.util.List;

public class DataframesFactory {

    public static Dataset<Row> createRegimeDataFrame(SparkSession spark, Regime regime) {

        // Créer une liste pour stocker les lignes
        List<Row> regimeRows = new ArrayList<>();

        String nom_regime = regime.getName();

        StructType regimeSchema = new StructType()
                .add("nom_regime", DataTypes.StringType);

        // Créer la ligne de données
        Row regimeRow = RowFactory.create(nom_regime);

        // Ajouter au DataFrame
        regimeRows.add(regimeRow);

        // Créer le DataFrame
        Dataset<Row> regimeDF = spark.createDataFrame(regimeRows, regimeSchema);

        regimeDF.show();
        return regimeDF;
    }

    public static Dataset<Row> createJourDataFrame(SparkSession spark, Jour jour){
        // Créer une liste pour stocker les lignes
        List<Row> joursRows = new ArrayList<>();

        String jour_nom = jour.getName();

        StructType jourSchema = new StructType()
                .add("jour_nom", DataTypes.StringType);

        // Créer la ligne de données
        Row jourRow = RowFactory.create(
                jour_nom);

        // Ajouter au DataFrame
        joursRows.add(jourRow);

        // Créer le DataFrame
        Dataset<Row> jourDF = spark.createDataFrame(joursRows, jourSchema);

        jourDF.show();
        return jourDF;
    }


    public static Dataset<Row> createUtilisateurDataFrame(SparkSession spark, Utilisateur utilisateur) {
        // Créer une liste pour stocker les lignes
        List<Row> utilisateurRows = new ArrayList<>();

        // Récupérer les informations de l'utilisateur
        String nom_utilisateur = utilisateur.getNom(); // Assurez-vous que cette méthode existe
        int taille = utilisateur.getTaille(); // Assurez-vous que l'attribut taille existe et a un getter
        String sexe = utilisateur.getSexe().toString(); // Convertir l'enum Sexe en String
        float poids = utilisateur.getPoids();
        float imc = utilisateur.getImc(); // Assurez-vous que l'attribut imc existe et a un getter
        int regimeID = utilisateur.getRegime(); // Assurez-vous que la méthode getId existe pour Regime

        // Définir le schéma avec la colonne IMC incluse
        StructType utilisateurSchema = new StructType()
                .add("nom_utilisateur", DataTypes.StringType)
                .add("taille", DataTypes.IntegerType)
                .add("sexe", DataTypes.StringType)
                .add("poids", DataTypes.FloatType)
                .add("imc", DataTypes.FloatType)
                .add("regime_id", DataTypes.IntegerType);

        // Créer la ligne de données
        Row utilisateurRow = RowFactory.create(
                nom_utilisateur,
                taille,
                sexe,
                poids,
                imc,
                regimeID);

        // Ajouter la ligne au DataFrame
        utilisateurRows.add(utilisateurRow);

        // Créer le DataFrame
        Dataset<Row> utilisateurDF = spark.createDataFrame(utilisateurRows, utilisateurSchema);

        utilisateurDF.show();
        return utilisateurDF;
    }

    public static Dataset<Row> createUtilisateurRepaDataFrame(SparkSession spark, UtilisateurRepa utilisateurRepas){
        // Créer une liste pour stocker les lignes
        List<Row> rows = new ArrayList<>();

        // Récupère les infos de la joinure
        int utilisateurId = utilisateurRepas.getUtilisateurId();
        int repaId = utilisateurRepas.getRepaId();

        // Définir le schema
        StructType schema = new StructType()
                .add("utilisateur_id", DataTypes.IntegerType)
                .add("repa_id", DataTypes.IntegerType);

        // Construire la row
        Row row = RowFactory.create(utilisateurId, repaId);

        // Ajouter au DataFrame
        rows.add(row);

        return spark.createDataFrame(rows, schema);
    }


}
