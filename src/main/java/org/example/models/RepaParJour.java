package org.example.models;

import java.util.List;

public class RepaParJour {
    private String jour;
    private List<Repa> repas;

    public RepaParJour(String jour, List<Repa> repas) {
        this.jour = jour;
        this.repas = repas;
    }

    // Getters
    public String getJour() {
        return jour;
    }

    public List<Repa> getRepas() {
        return repas;
    }
}

