package org.example.models;

public class Jour {
    int id;
    String jour_nom;

    public Jour(String name) {
        this.jour_nom = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return jour_nom;
    }

    public void setName(String name) {
        this.jour_nom = name;
    }
}
