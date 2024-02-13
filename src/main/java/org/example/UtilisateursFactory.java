package org.example;

import org.example.models.Sexe;
import org.example.models.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateursFactory {
    public static Utilisateur createUtilisateur(String nom_utilisateur, int taille, Sexe sexe, float poids, int regimeID) {
        // Conversion de la taille de centimètres en mètres pour le calcul de l'IMC
        float tailleEnMetres = taille / 100f;
        float imc = calculerIMC(poids, tailleEnMetres);

        // Création d'un nouvel utilisateur avec l'IMC calculé
        return new Utilisateur(nom_utilisateur, taille, sexe, poids, imc, regimeID);
    }

    public static float calculerIMC(float poids, float tailleEnMetres) {
        return poids / (tailleEnMetres * tailleEnMetres);
    }

    public static float getIMC(Connection conn, String nomUtilisateur) {
        String sql = "SELECT imc FROM utilisateurs WHERE nom_utilisateur = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomUtilisateur);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("imc");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'IMC pour " + nomUtilisateur + ": " + e.getMessage());
        }
        return -1; // Valeur par défaut indiquant que l'IMC n'a pas pu être récupéré
    }

}
