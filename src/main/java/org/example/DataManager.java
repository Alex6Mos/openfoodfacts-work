package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.models.Sexe;
import org.example.models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.example.database.DatabaseManager.creerEtEnregistrerUtilisateur;

public class DataManager {
    public static void processData(SparkSession spark, Connection connection, Dataset<Row> dataCleaned) throws SQLException {

        int regimeID = RegimesFactory.getRegimeIdByName(connection, "Hypoglycémique");
        creerEtEnregistrerUtilisateur(connection, "Jean", 175, Sexe.Homme, 80 , regimeID);

        if (regimeID > 0) {
            float imc = UtilisateursFactory.getIMC(connection, "Jean");
            if (imc != -1) {
                List<Jour> joursDeLaSemaine = JourFactory.getAllJours(connection);
                List<RepaParJour> repasSemaine = MealGenerator.generateBalancedMeals(dataCleaned, connection, imc);
                for (RepaParJour repaJour : repasSemaine) {
                    System.out.println("------------Jour : " + repaJour.getJour() + "---------");
                    for (Repa repa : repaJour.getRepas()) {
                        System.out.println("Période : " + repa.getPeriode() + " : ");
                        System.out.println("Aliments : ");
                        for (Aliment aliment : repa.getAliments()) {
                            System.out.println("- " + aliment.getNom() +
                                    " | Calories : " + aliment.getCalories() + " kcal | " +
                                    "Protéines : " + aliment.getProteines() + " g | " +
                                    "Glucides : " + aliment.getGlucides() + " g | " +
                                    "Lipides : " + aliment.getLipides() + " g");
                        }
                        System.out.println("Total repas : " +
                                repa.getTotalKcal() + " kcal | " +
                                repa.getTotalProteines() + " g protéines | " +
                                repa.getTotalGlucides() + " g glucides | " +
                                repa.getTotalLipides() + " g lipides");
                        System.out.println("--------------------------------");
                    }
                }
                System.out.println("\nGénération des repas terminée!");
            } else {
                System.out.println("IMC de Jean introuvable.");
            }
        } else {
            System.out.println("Régime spécifié introuvable.");
        }
    }
}
