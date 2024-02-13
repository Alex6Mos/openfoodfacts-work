package org.example;

import org.example.models.Jour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JourFactory {


    public static List<Jour> getAllJours(Connection connection) {
        List<Jour> jours = new ArrayList<>();
        String sql = "SELECT * FROM jours"; // Assurez-vous que la table s'appelle "jours" et a une colonne "jour_nom"

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Jour jour = new Jour(rs.getString("jour_nom"));
                jour.setId(rs.getInt("id")); // Assurez-vous que votre table a une colonne "id"
                jours.add(jour);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des jours de la semaine: " + e.getMessage());
        }

        return jours;
    }
}

