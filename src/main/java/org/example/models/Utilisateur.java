package org.example.models;

public  class Utilisateur {

    int id;
    private String nom_utilisateur;
    private int taille;
    private Sexe sexe;
    private float poids;
    float imc;
    int regime;
    public Utilisateur(String nom_utilisateur, int taille, Sexe sexe, float poids, float imc , int regime) {
        this.nom_utilisateur = nom_utilisateur;
        this.taille = taille;
        this.sexe = sexe;
        this.poids = poids;
        this.imc = imc;
        this.regime = regime;
    }

    public String getNom() {
        return nom_utilisateur;
    }

    public int getAge() {
        return taille;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public float getPoids() {
        return poids;
    }

    public void setNom(String nom) {
        this.nom_utilisateur = nom;
    }

    public void setTaille(int age) {
        this.taille = age;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public int getRegime() {
        return regime;
    }

    public void setRegime(int regime) {
        this.regime = regime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public int getTaille() {
        return taille;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }
}
