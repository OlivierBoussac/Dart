package com.example.dart.object;

import java.io.Serializable;
import java.util.ArrayList;

public class ParamGame implements Serializable {
    private boolean equipe;
    private ArrayList<Joueur> joueur;
    private int score;
    private String debut;
    private String fin;

    // Getters et setters
    public boolean isEquipe() {
        return equipe;
    }

    public void setEquipe(boolean equipe) {
        this.equipe = equipe;
    }

    public ArrayList<Joueur> getJoueur() {
        return joueur;
    }

    public void setJoueur(ArrayList<Joueur> joueur) {
        this.joueur = joueur;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
