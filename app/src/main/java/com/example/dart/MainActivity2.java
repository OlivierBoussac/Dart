package com.example.dart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

    private LinearLayout containerPlayers;
    private int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Retrieve the number of players from the intent
        numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 0);

        // Find the LinearLayout container for players
        containerPlayers = findViewById(R.id.containerPlayers);

        // Find home button
        Button buttonHome = findViewById(R.id.buttonHome);

        // Use home button in order to display dialog for validation
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupLeave();
            }
        });

        // Dynamically add views for each player
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 1; i <= numberOfPlayers; i++) {

            Log.i("MainActivity", "i");

            // Inflate player_item.xml and get the root view
            View playerView = inflater.inflate(R.layout.player_item, containerPlayers, false);

            // Find and set up the views in the player layout
            EditText playerName = playerView.findViewById(R.id.playerName);
            TextView scoreText = playerView.findViewById(R.id.scoreText);
            EditText valueInput = playerView.findViewById(R.id.valueInput);
            Button okButton = playerView.findViewById(R.id.okButton);

            // Set initial values
            playerName.setText("Joueur " + i);
            scoreText.setText("301");

            // Set up OK button click listener
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredValueStr = valueInput.getText().toString();

                    if (!enteredValueStr.isEmpty()) {
                        try {
                            int currentValue = Integer.parseInt(scoreText.getText().toString());
                            int enteredValue = Integer.parseInt(enteredValueStr);

                            // Perform the subtraction
                            int newValue = currentValue - enteredValue;

                            // Ensure the new value is valid
                            if (enteredValue <= currentValue || enteredValue < 0) {
                                if (newValue >= 0) {
                                    scoreText.setText(String.valueOf(newValue));
                                    if (newValue == 0) {
                                        showWinnerDialog(playerName.getText().toString());
                                    }
                                } else {
                                    Toast.makeText(MainActivity2.this, "Le score ne peut pas être inférieur à 0.", Toast.LENGTH_SHORT).show();
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

            // Add the inflated view to the container
            containerPlayers.addView(playerView);
        }
    }

    private void showPopupLeave() {
        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_validation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        // Set up buttons
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

        // Set up the custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Get the TextView and set the winner name
        TextView tvWinnerName = dialogView.findViewById(R.id.tvWinnerName);
        tvWinnerName.setText("Le gagnant est " + winnerName);

        // Set up buttons
        Button btnRestart = dialogView.findViewById(R.id.btnRestart);
        Button btnHome = dialogView.findViewById(R.id.btnHome);

        AlertDialog alertDialog = builder.create();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sauvegarder les noms des joueurs
                SharedPreferences sharedPreferences = getSharedPreferences("player_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                for (int i = 0; i < containerPlayers.getChildCount(); i++) {
                    LinearLayout playerLayout = (LinearLayout) containerPlayers.getChildAt(i);
                    EditText playerEditText = (EditText) playerLayout.getChildAt(0);
                    editor.putString("player" + i, playerEditText.getText().toString());
                }
                editor.putInt("numberOfPlayers", numberOfPlayers);
                editor.apply();

                alertDialog.dismiss();
                // Restart the activity
                recreate(); // For simplicity, you can restart the activity
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
}