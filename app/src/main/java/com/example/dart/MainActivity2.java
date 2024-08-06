package com.example.dart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private int numberOfPlayers;
    private LinearLayout containerPlayers;
    private int marginInPx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 0);
        containerPlayers = findViewById(R.id.containerPlayers);
        Button buttonHome = findViewById(R.id.buttonHome);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupLeave();
            }
        });

        int marginInDp = 16;
        final float scale = getResources().getDisplayMetrics().density;
        marginInPx = (int) (marginInDp * scale + 0.5f);

        if (savedInstanceState == null) {
            for (int i = 1; i <= numberOfPlayers; i++) {
                addPlayerLayout("Joueur " + i, 301);
            }
        } else {
            String[] playerNames = savedInstanceState.getStringArray("playerNames");
            int[] playerScores = savedInstanceState.getIntArray("playerScores");

            if (playerNames != null && playerScores != null) {
                for (int i = 0; i < playerNames.length; i++) {
                    addPlayerLayout(playerNames[i], playerScores[i]);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int childCount = containerPlayers.getChildCount();
        String[] playerNames = new String[childCount];
        int[] playerScores = new int[childCount];

        for (int i = 0; i < childCount; i++) {
            LinearLayout playerLayout = (LinearLayout) containerPlayers.getChildAt(i);
            EditText playerEditText = (EditText) playerLayout.findViewById(R.id.playerName);
            TextView scoreText = (TextView) playerLayout.findViewById(R.id.scoreText);

            playerNames[i] = playerEditText.getText().toString();
            playerScores[i] = Integer.parseInt(scoreText.getText().toString());
        }

        outState.putStringArray("playerNames", playerNames);
        outState.putIntArray("playerScores", playerScores);
        outState.putInt("numberOfPlayers", childCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String[] playerNames = savedInstanceState.getStringArray("playerNames");
        int[] playerScores = savedInstanceState.getIntArray("playerScores");
        int numberOfPlayers = savedInstanceState.getInt("numberOfPlayers");

        if (playerNames != null && playerScores != null) {
            containerPlayers.removeAllViews();

            for (int i = 0; i < numberOfPlayers; i++) {
                addPlayerLayout(playerNames[i], playerScores[i]);
            }
        }
    }

    private void addPlayerLayout(String playerName, int playerScore) {
        LinearLayout playerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.player_item, containerPlayers, false);

        EditText playerEditText = playerLayout.findViewById(R.id.playerName);
        playerEditText.setText(playerName);

        TextView scoreText = playerLayout.findViewById(R.id.scoreText);
        scoreText.setText(String.valueOf(playerScore));

        EditText valueInput = playerLayout.findViewById(R.id.valueInput);
        Button okButton = playerLayout.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredValueStr = valueInput.getText().toString();

                if (!enteredValueStr.isEmpty()) {
                    try {
                        int currentValue = Integer.parseInt(scoreText.getText().toString());
                        int enteredValue = Integer.parseInt(enteredValueStr);

                        int newValue = currentValue - enteredValue;

                        if (enteredValue <= currentValue || enteredValue < 0) {
                            if (enteredValue <= 180) {
                                if (newValue >= 0) {
                                    scoreText.setText(String.valueOf(newValue));
                                    if (newValue == 0) {
                                        showWinnerDialog(playerEditText.getText().toString());
                                    }
                                    valueInput.setText(""); // Clear the input field after updating the score
                                } else {
                                    Toast.makeText(MainActivity2.this, "Le score ne peut pas être inférieur à 0.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity2.this, "La valeur entrée est supérieure à la valeur maximale possible.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity2.this, "La valeur entrée est supérieure au score actuel.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity2.this, "Valeur entrée invalide.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Veuillez entrer une valeur.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        containerPlayers.addView(playerLayout);
    }

    private void showPopupLeave() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_validation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnNo.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void showWinnerDialog(String winnerName) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_winner, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        TextView tvWinnerName = dialogView.findViewById(R.id.tvWinnerName);
        tvWinnerName.setText("Le gagnant est " + winnerName);

        Button btnRestart = dialogView.findViewById(R.id.btnRestart);
        Button btnHome = dialogView.findViewById(R.id.btnHome);

        AlertDialog alertDialog = builder.create();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScores();
                alertDialog.dismiss();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.show();
    }

    private void resetScores() {
        for (int i = 0; i < containerPlayers.getChildCount(); i++) {
            LinearLayout playerLayout = (LinearLayout) containerPlayers.getChildAt(i);
            TextView scoreText = playerLayout.findViewById(R.id.scoreText);
            scoreText.setText("301");
        }
    }
}
