package org.example.models;

import java.util.List;

public class Repa {
    int id;
    String periode;

    float totalKcal, totalProteines, totalGlucides, totalLipides;

    List<Aliment> aliments;
    public Repa(List<Aliment> aliments) {
        this.aliments = aliments;
    }

    public Repa(String periode, List<Aliment> aliments, float totalKcal, float  totalProteines, float totalGlucides, float totalLipides) {
        this.periode = periode;
        this.aliments = aliments;
        this.totalKcal = totalKcal;
        this.totalProteines = totalProteines;
        this.totalGlucides = totalGlucides;
        this.totalLipides = totalLipides;
    }

    public Repa(List<Aliment> alimentsChoisis, int totalKcal, int totalProteines, int totalGlucides, int totalLipides) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public List<org.example.models.Aliment> getAliments() {
        return aliments;
    }

    public void setAliments(List<org.example.models.Aliment> aliments) {
        this.aliments = aliments;
    }


    public float getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(int totalKcal) {
        this.totalKcal = totalKcal;
    }

    public float getTotalProteines() {
        return totalProteines;
    }

    public void setTotalProteines(int totalProteines) {
        this.totalProteines = totalProteines;
    }

    public float getTotalGlucides() {
        return totalGlucides;
    }

    public void setTotalGlucides(int totalGlucides) {
        this.totalGlucides = totalGlucides;
    }

    public float getTotalLipides() {
        return totalLipides;
    }

    public void setTotalLipides(int totalLipides) {
        this.totalLipides = totalLipides;
    }
}
