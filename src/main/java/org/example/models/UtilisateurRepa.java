package org.example.models;

public class UtilisateurRepa {

    private int utilisateurId;
    private int repaId;

    public UtilisateurRepa(int utilisateurId, int repaId) {
        this.utilisateurId = utilisateurId;
        this.repaId = repaId;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getRepaId() {
        return repaId;
    }

    public void setRepaId(int repasId) {
        this.repaId = repasId;
    }

}
