package org.example;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.spark.sql.functions.col;


public class MealGenerator {
    public static List<RepaParJour> generateBalancedMeals(Dataset<Row> alimentsFiltered, Connection connection, float imc,  String nomRegime) {
        List<Jour> joursDeLaSemaine = JourFactory.getAllJours(connection);
        List<RepaParJour> repasParSemaine = new ArrayList<>();

        for (Jour jour : joursDeLaSemaine) {
            NutritionSeuil breakfastSeuil, lunchSeuil, dinnerSeuil;

            // Déterminer les seuils en fonction de l'IMC et du régime
            switch (nomRegime) {
                case "Hypoglycémique":
                    if (imc < 18.5) {
                        breakfastSeuil = new NutritionSeuil(500, 700, 30, 40, 70, 100, 15, 25);
                        lunchSeuil = new NutritionSeuil(700, 900, 40, 50, 80, 120, 20, 30);
                        dinnerSeuil = new NutritionSeuil(600, 800, 30, 40, 60, 100, 20, 30);
                    } else if (imc < 25) {
                        breakfastSeuil = new NutritionSeuil(300, 500, 15, 25, 40, 60, 10, 20);
                        lunchSeuil = new NutritionSeuil(400, 600, 20, 40, 50, 90, 10, 30);
                        dinnerSeuil = new NutritionSeuil(350, 550, 20, 30, 40, 70, 10, 25);
                    } else if (imc < 30) {
                        breakfastSeuil = new NutritionSeuil(250, 400, 15, 20, 30, 50, 5, 15);
                        lunchSeuil = new NutritionSeuil(300, 500, 20, 30, 40, 60, 5, 10);
                        dinnerSeuil = new NutritionSeuil(250, 450, 15, 25, 30, 50, 5, 10);
                    } else {
                        breakfastSeuil = new NutritionSeuil(200, 350, 15, 20, 20, 40, 0, 5);
                        lunchSeuil = new NutritionSeuil(250, 400, 20, 30, 30, 50, 0, 10);
                        dinnerSeuil = new NutritionSeuil(200, 350, 15, 25, 20, 40, 0, 5);
                    }
                    break;
                case "Fodmap":
                    if (imc < 18.5) {
                        breakfastSeuil = new NutritionSeuil(450, 650, 25, 35, 60, 85, 10, 20);
                        lunchSeuil = new NutritionSeuil(600, 800, 35, 45, 70, 110, 15, 25);
                        dinnerSeuil = new NutritionSeuil(550, 750, 30, 40, 50, 90, 15, 20);
                    } else if (imc < 25) {
                        breakfastSeuil = new NutritionSeuil(350, 550, 20, 30, 45, 70, 5, 15);
                        lunchSeuil = new NutritionSeuil(500, 700, 30, 40, 60, 100, 10, 20);
                        dinnerSeuil = new NutritionSeuil(450, 650, 25, 35, 50, 80, 10, 15);
                    } else if (imc < 30) {
                        breakfastSeuil = new NutritionSeuil(300, 500, 15, 25, 35, 60, 5, 10);
                        lunchSeuil = new NutritionSeuil(450, 650, 20, 30, 40, 70, 5, 10);
                        dinnerSeuil = new NutritionSeuil(400, 600, 20, 25, 35, 60, 5, 10);
                    } else {
                        breakfastSeuil = new NutritionSeuil(250, 450, 10, 20, 20, 40, 0, 5);
                        lunchSeuil = new NutritionSeuil(400, 600, 15, 25, 25, 45, 0, 5);
                        dinnerSeuil = new NutritionSeuil(350, 550, 15, 20, 20, 40, 0, 5);
                    }
                    break;
                case "Méditerranéen":
                    if (imc < 18.5) {
                        breakfastSeuil = new NutritionSeuil(400, 600, 20, 25, 60, 80, 15, 20);
                        lunchSeuil = new NutritionSeuil(550, 750, 30, 40, 70, 110, 20, 30);
                        dinnerSeuil = new NutritionSeuil(500, 700, 25, 35, 50, 90, 20, 25);
                    } else if (imc < 25) {
                        breakfastSeuil = new NutritionSeuil(350, 550, 18, 23, 50, 70, 12, 18);
                        lunchSeuil = new NutritionSeuil(500, 700, 25, 35, 60, 100, 15, 25);
                        dinnerSeuil = new NutritionSeuil(450, 650, 20, 30, 40, 80, 12, 18);
                    } else if (imc < 30) {
                        breakfastSeuil = new NutritionSeuil(300, 500, 15, 20, 40, 60, 10, 15);
                        lunchSeuil = new NutritionSeuil(450, 650, 20, 30, 50, 80, 12, 18);
                        dinnerSeuil = new NutritionSeuil(400, 600, 15, 25, 35, 70, 10, 15);
                    } else {
                        breakfastSeuil = new NutritionSeuil(250, 400, 12, 18, 30, 50, 8, 12);
                        lunchSeuil = new NutritionSeuil(400, 600, 15, 25, 40, 70, 10, 15);
                        dinnerSeuil = new NutritionSeuil(350, 550, 12, 18, 30, 60, 8, 12);
                    }
                    break;
                default:
                    // Valeurs par défaut au cas où le régime n'est pas reconnu
                    breakfastSeuil = new NutritionSeuil(500, 700, 30, 40, 70, 100, 15, 25);
                    lunchSeuil = new NutritionSeuil(700, 900, 40, 50, 80, 120, 20, 30);
                    dinnerSeuil = new NutritionSeuil(600, 800, 30, 40, 60, 100, 20, 30);
                    break;
            }

            // Génération des repas pour chaque période de la journée
            Repa breakfastMeal = generateMeal(alimentsFiltered, breakfastSeuil, "Petit-déjeuner");
            Repa lunchMeal = generateMeal(alimentsFiltered, lunchSeuil, "Déjeuner");
            Repa dinnerMeal = generateMeal(alimentsFiltered, dinnerSeuil, "Dîner");

            // Créer un objet RepaParJour pour chaque jour avec les repas générés
            List<Repa> repasDuJour = new ArrayList<>();
            repasDuJour.add(breakfastMeal);
            repasDuJour.add(lunchMeal);
            repasDuJour.add(dinnerMeal);

            repasParSemaine.add(new RepaParJour(jour.getName(), repasDuJour));
        }


        return repasParSemaine;
    }


    private static Repa generateMeal(Dataset<Row> alimentsFiltered, NutritionSeuil seuil, String periode) {
        Dataset<Row> repasFiltre;
        if ("breakfast".equals(periode)) {
            repasFiltre = alimentsFiltered.filter(col("categories_tags").contains("breakfast"));
        } else {
            repasFiltre = alimentsFiltered.filter(col("categories_tags").contains("meals"));
        }

        // Convertir la liste immuable en une liste modifiable
        List<Row> alimentsPossibles = new ArrayList<>(repasFiltre.collectAsList());
        Random random = new Random();
        List<Aliment> alimentsChoisis = new ArrayList<>();

        float totalKcal = 0, totalProteines = 0, totalGlucides = 0, totalLipides = 0;

        while (!alimentsPossibles.isEmpty() &&
                (totalKcal < seuil.getKcalMin() || totalProteines < seuil.getProteinesMin() ||
                        totalGlucides < seuil.getGlucidesMin() || totalLipides < seuil.getLipidesMin())) {

            int index = random.nextInt(alimentsPossibles.size());
            Row aliment = alimentsPossibles.get(index);

            float kcal = Float.parseFloat(aliment.getAs("energy-kcal_100g"));
            float proteines = Float.parseFloat(aliment.getAs("proteins_100g"));
            float glucides = Float.parseFloat(aliment.getAs("carbohydrates_100g"));
            float lipides = Float.parseFloat(aliment.getAs("fat_100g"));

            if ((totalKcal + kcal <= seuil.getKcalMax()) && (totalProteines + proteines <= seuil.getProteinesMax()) &&
                    (totalGlucides + glucides <= seuil.getGlucidesMax()) && (totalLipides + lipides <= seuil.getLipidesMax())) {

                alimentsChoisis.add(new Aliment(aliment.getAs("product_name"), kcal, proteines, glucides, lipides));
                totalKcal += kcal;
                totalProteines += proteines;
                totalGlucides += glucides;
                totalLipides += lipides;
            }

            // Maintenant, retirer l'aliment est supporté car la liste est modifiable
            alimentsPossibles.remove(index);
        }

        return new Repa(periode, alimentsChoisis, totalKcal, totalProteines, totalGlucides, totalLipides);
    }


}
