package org.example.models;

public class Aliment {
    String nom;
    float calories;
    float proteines;
    float glucides;
    float lipides;

    public Aliment(String nom, float calories, float proteines, float glucides, float lipides) {
        this.nom = nom;
        this.calories = calories;
        this.proteines = proteines;
        this.glucides = glucides;
        this.lipides = lipides;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public float getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getProteines() {
        return proteines;
    }

    public void setProteines(int proteines) {
        this.proteines = proteines;
    }

    public float getGlucides() {
        return glucides;
    }

    public void setGlucides(int glucides) {
        this.glucides = glucides;
    }

    public float getLipides() {
        return lipides;
    }

    public void setLipides(int lipides) {
        this.lipides = lipides;
    }
}

