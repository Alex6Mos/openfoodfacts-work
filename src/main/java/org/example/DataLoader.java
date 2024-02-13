package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataLoader {
    public static Dataset<Row> loadDataAndClean(SparkSession spark) {
        // Chargement des données
        Dataset<Row> data = spark.read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", "\t")
                .load("en.openfoodfacts.org.products.csv");

        // Sélection des colonnes spécifiques
        Dataset<Row> dataFiltered = data.select(
                data.col("product_name"),
                data.col("categories_tags"),
                data.col("energy-kcal_100g"),
                data.col("proteins_100g"),
                data.col("carbohydrates_100g"),
                data.col("fat_100g")
        );

        // Suppression de toutes les lignes possédant au moins une valeur nulle


        return dataFiltered.na().drop();
    }
}
