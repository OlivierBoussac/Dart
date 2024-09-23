package com.example.dart.object;

import java.io.Serializable;

public class Joueur implements Serializable {
    private String name;
    private int equipe;

    public Joueur(String name, int equipe) {
        this.name = name;
        this.equipe = equipe;
    }

    public String getName() {
        return name;
    }

    public int getEquipe() {
        return equipe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEquipe(int equipe) {
        this.equipe = equipe;
    }
}
