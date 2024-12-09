package com.example.dart.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dart.object.AllStatistique;
import com.example.dart.object.StatistiqueInGame;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "Statistique";
    private static final int DB_VERSION = 3;

    private static final String TABLE_NAME_1 = "Statistique";
    private static final String TABLE_NAME_2 = "StatistiqueInGame";
    private static final String TABLE_NAME_3 = "StatistiqueHistoriqueScore";

    private static final String ID_COL = "id";
    private static final String ID = "id";
    private static final String NAME_COL = "name";
    private static final String SCORE_TOTAL_COL = "score";
    private static final String WINNER_NAME_COL = "winner_name"; // Colonne pour le nom du gagnant
    private static final String GAME_ID_COL = "game_id"; // Colonne pour l'ID du jeu
    private static final String DERNIER_COUP_COL = "dernierCoup";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatistiqueTable = "CREATE TABLE " + TABLE_NAME_1 + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WINNER_NAME_COL + " TEXT, " +
                GAME_ID_COL + " INTEGER, " +
                "FOREIGN KEY(" + GAME_ID_COL + ") REFERENCES " + TABLE_NAME_3 + "(" + ID_COL + ") ON DELETE SET NULL" +
                ")";

        String createStatistiqueInGameTable = "CREATE TABLE " + TABLE_NAME_2 + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT, " +
                SCORE_TOTAL_COL + " TEXT, " +
                DERNIER_COUP_COL + " TEXT" +
                ")";

        String createStatistiqueHistoriqueScoreTable = "CREATE TABLE " + TABLE_NAME_3 + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                SCORE_TOTAL_COL + " TEXT, " +
                DERNIER_COUP_COL + " TEXT" +
                ")";

        db.execSQL(createStatistiqueTable);
        db.execSQL(createStatistiqueInGameTable);
        db.execSQL(createStatistiqueHistoriqueScoreTable);
    }

    // Méthode pour ajouter une nouvelle statistique avec l'ID du gagnant
    public void addNewStatistique(String winnerName, Integer gameId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WINNER_NAME_COL, winnerName);
        values.put(GAME_ID_COL, gameId);

        db.insert(TABLE_NAME_1, null, values);
        db.close();
    }

    // Méthode pour ajouter une nouvelle statistique en cours de jeu
    public void addNewStatistiqueInGame(String playerName, String scoreTotal, String dernierScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_COL, playerName);
        values.put(SCORE_TOTAL_COL, scoreTotal);
        values.put(DERNIER_COUP_COL, dernierScore);

        db.insert(TABLE_NAME_2, null, values);
        db.close();
    }

    public void addNewHistoriqueScore(int id, String playerName, String totalScore, String lastScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME_COL, playerName);
        values.put(SCORE_TOTAL_COL, totalScore);
        values.put(DERNIER_COUP_COL, lastScore);

        db.insert(TABLE_NAME_3, null, values);
        db.close();
    }

    public List<AllStatistique> getStatistique() {
        List<AllStatistique> statsList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_1, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AllStatistique stat = new AllStatistique(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(WINNER_NAME_COL)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(GAME_ID_COL))
                    );
                    statsList.add(stat);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DBHandler", "Error while trying to get statistique", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return statsList;
    }

    public List<StatistiqueInGame> getStatistiqueInGame() {
        List<StatistiqueInGame> statsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2, null);

        if (cursor.moveToFirst()) {
            do {
                StatistiqueInGame stat = new StatistiqueInGame(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SCORE_TOTAL_COL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DERNIER_COUP_COL))
                );
                statsList.add(stat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return statsList;
    }

    public void clearStatistiqueInGame() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_2, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        onCreate(db);
    }
}
