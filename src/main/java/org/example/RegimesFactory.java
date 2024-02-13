package org.example;

import org.example.models.Regime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegimesFactory {
    public static Regime createFodmapRegime() {
        return new Regime("Fodmap");
    }

    public static Regime createMediterraneanRegime() {
        return new Regime("Mediterranean");
    }

    public static Regime createHypoglycemicRegime() {
        return new Regime("Hypoglycémique");
    }


    public static Regime getRegimeByName(Connection conn, String nomRegime) {
        // La requête SQL pour sélectionner le régime par nom
        String sql = "SELECT id, nom_regime FROM regimes WHERE nom_regime = ?";

        try {
            // Préparer la requête SQL pour éviter les injections SQL
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nomRegime);

            // Exécuter la requête
            ResultSet rs = pstmt.executeQuery();

            // Vérifier si un résultat est retourné
            if (rs.next()) {
                // Récupérer les données du régime
                int id = rs.getInt("id");
                String nom = rs.getString("nom_regime");

                // Créer et retourner l'objet Regime
                return new Regime(nom);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du régime par nom : " + e.getMessage());
        }

        // Retourner null si aucun régime n'est trouvé ou en cas d'erreur
        return null;
    }

    public static int getRegimeIdByName(Connection conn, String nomRegime) {
        // La requête SQL pour sélectionner l'ID du régime par son nom
        String sql = "SELECT id FROM regimes WHERE nom_regime = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Définir le paramètre de la requête
            pstmt.setString(1, nomRegime);

            // Exécuter la requête
            ResultSet rs = pstmt.executeQuery();

            // Vérifier si un résultat est retourné
            if (rs.next()) {
                // Récupérer l'ID du régime
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'ID du régime par nom: " + e.getMessage());
            // Vous pouvez également logger l'erreur selon votre framework de logging
        }

        // Retourner une valeur indiquant que le régime n'a pas été trouvé
        return -1;
    }
}

