package com.example.dart.object;

public class AllStatistique {
    private int id;
    private String winner_name;
    private int game_ID;
    private String createdAt;

    public AllStatistique(int id, String winner_name, int game_ID) {
        this.id = id;
        this.winner_name = winner_name;
        this.game_ID = game_ID;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getWinner_name() {
        return winner_name;
    }

    public void setWinner_name(String winner_name) {
        this.winner_name = winner_name;
    }

    public int getGame_ID() {
        return game_ID;
    }

    public void setGame_ID(int game_ID) {
        this.game_ID = game_ID;
    }
}
