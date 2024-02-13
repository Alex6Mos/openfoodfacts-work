package org.example.models;

public class Menu {
    int id;
    String nom_menu;
    Jour jour;
    Utilisateur utilisateur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNom_menu() {
        return nom_menu;
    }

    public void setNom_menu(String nom_menu) {
        this.nom_menu = nom_menu;
    }
}