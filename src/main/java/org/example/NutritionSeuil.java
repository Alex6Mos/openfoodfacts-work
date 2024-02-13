package org.example;

public class NutritionSeuil {
    private int kcalMin, kcalMax;
    private int proteinesMin, proteinesMax;
    private int glucidesMin, glucidesMax;
    private int lipidesMin, lipidesMax;

    // Constructeur
    public NutritionSeuil(int kcalMin, int kcalMax, int proteinesMin, int proteinesMax, int glucidesMin, int glucidesMax, int lipidesMin, int lipidesMax) {
        this.kcalMin = kcalMin;
        this.kcalMax = kcalMax;
        this.proteinesMin = proteinesMin;
        this.proteinesMax = proteinesMax;
        this.glucidesMin = glucidesMin;
        this.glucidesMax = glucidesMax;
        this.lipidesMin = lipidesMin;
        this.lipidesMax = lipidesMax;
    }

    // Getters
    public int getKcalMin() {
        return kcalMin;
    }

    public int getKcalMax() {
        return kcalMax;
    }

    public int getProteinesMin() {
        return proteinesMin;
    }

    public int getProteinesMax() {
        return proteinesMax;
    }

    public int getGlucidesMin() {
        return glucidesMin;
    }

    public int getGlucidesMax() {
        return glucidesMax;
    }

    public int getLipidesMin() {
        return lipidesMin;
    }

    public int getLipidesMax() {
        return lipidesMax;
    }

    // Setters
    public void setKcalMin(int kcalMin) {
        this.kcalMin = kcalMin;
    }

    public void setKcalMax(int kcalMax) {
        this.kcalMax = kcalMax;
    }

    public void setProteinesMin(int proteinesMin) {
        this.proteinesMin = proteinesMin;
    }

    public void setProteinesMax(int proteinesMax) {
        this.proteinesMax = proteinesMax;
    }

    public void setGlucidesMin(int glucidesMin) {
        this.glucidesMin = glucidesMin;
    }

    public void setGlucidesMax(int glucidesMax) {
        this.glucidesMax = glucidesMax;
    }

    public void setLipidesMin(int lipidesMin) {
        this.lipidesMin = lipidesMin;
    }

    public void setLipidesMax(int lipidesMax) {
        this.lipidesMax = lipidesMax;
    }
}

