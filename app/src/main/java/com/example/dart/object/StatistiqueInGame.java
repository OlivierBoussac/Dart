package com.example.dart.object;

public class StatistiqueInGame {
    private int id;
    private String name;
    private String scoreTotal;
    private String dernierCoup;

    public StatistiqueInGame(int id, String name, String scoreTotal, String dernierCoup) {
        this.id = id;
        this.name = name;
        this.scoreTotal = scoreTotal;
        this.dernierCoup = dernierCoup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(String scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getDernierCoup() {
        return dernierCoup;
    }

    public void setDernierCoup(String dernierCoup) {
        this.dernierCoup = dernierCoup;
    }
}