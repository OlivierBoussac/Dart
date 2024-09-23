package com.example.dart.Page;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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

import com.example.dart.R;
import com.example.dart.object.ParamGame;

import java.util.ArrayList;
import java.util.List;

public class MenuInGame extends AppCompatActivity {

    private static final String KEY_PLAYER_SCORES = "player_scores";
    private static final String KEY_PLAYER_NAMES = "player_names";
    private static final String KEY_CURRENT_INDEX = "current_index";

    private List<Button> okButtons = new ArrayList<>();
    private int currentClickableButtonIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_in_game);

        // Retrieve the number of players and the selected rule from the intent
        ParamGame paramGame = (ParamGame) getIntent().getSerializableExtra("paramGame");//        String regleSelect = getIntent().getStringExtra("regle");

        // Find the LinearLayout container for players
        LinearLayout containerPlayers = findViewById(R.id.containerPlayers);

        // Find home button
        Button buttonHome = findViewById(R.id.buttonHome);

        // Use home button to display dialog for validation
        buttonHome.setOnClickListener(v -> showPopupLeave());

        // Define the margin value
        int marginInDp = 16;
        final float scale = getResources().getDisplayMetrics().density;
        int marginInPx = (int) (marginInDp * scale + 0.5f);

        // Dynamically add views for each player
        for (int i = 1; i <= paramGame.getJoueur().size(); i++) {
            // Inflate the player item layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View playerItemView = inflater.inflate(R.layout.player_item, containerPlayers, false);

            // Find and set up the player EditText for player name
            EditText playerEditText = playerItemView.findViewById(R.id.playerName);
            playerEditText.setText("Joueur " + i);

            // Find and set up the 301 TextView
            TextView textScore = playerItemView.findViewById(R.id.scoreText);
            textScore.setText(paramGame.getScore());

            // Find and set up the EditText for score input
            EditText editText = playerItemView.findViewById(R.id.valueInput);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)}); // Limit to 3 digits

            // Find and set up the OK Button
            Button btnOk = playerItemView.findViewById(R.id.okButton);

            // Add the OK button to the list
            okButtons.add(btnOk);

            btnOk.setOnClickListener(v -> {
                String enteredValueStr = editText.getText().toString();

                if (!enteredValueStr.isEmpty()) {
                    try {
                        int currentValue = Integer.parseInt(textScore.getText().toString());
                        int enteredValue = Integer.parseInt(enteredValueStr);

                        // Perform the subtraction
                        int newValue = currentValue - enteredValue;

                        // Ensure the new value is valid
                        if (enteredValue <= currentValue && enteredValue >= 0) {
                            if (enteredValue <= 180) {
                                if (newValue >= 0) {
                                    textScore.setText(String.valueOf(newValue));
                                    editText.setText(""); // Clear the input field

                                    if (newValue == 0) {
                                        showWinnerDialog(playerEditText.getText().toString());
                                    }

                                    // Disable the current button
                                    okButtons.get(currentClickableButtonIndex).setEnabled(false);

                                    // Move to the next button
                                    currentClickableButtonIndex = (currentClickableButtonIndex + 1) % okButtons.size();
                                    okButtons.get(currentClickableButtonIndex).setEnabled(true);
                                } else {
                                    Toast.makeText(MenuInGame.this, "Le score ne peut pas être inférieur à 0.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MenuInGame.this, "La valeur entrée est supérieure à la valeur maximale possible.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MenuInGame.this, "La valeur entrée est supérieure au score actuel.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MenuInGame.this, "Valeur entrée invalide.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MenuInGame.this, "Veuillez entrer une valeur.", Toast.LENGTH_SHORT).show();
                }
            });

            // Add the player item view to the container
            containerPlayers.addView(playerItemView);

            // Disable the OK button initially, except the first one
            if (i != 1) {
                btnOk.setEnabled(false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LinearLayout containerPlayers = findViewById(R.id.containerPlayers);
        int playerCount = containerPlayers.getChildCount();
        int[] playerScores = new int[playerCount];
        String[] playerNames = new String[playerCount];

        for (int i = 0; i < playerCount; i++) {
            View playerItemView = containerPlayers.getChildAt(i);
            TextView textScore = playerItemView.findViewById(R.id.scoreText);
            EditText playerEditText = playerItemView.findViewById(R.id.playerName);

            playerScores[i] = Integer.parseInt(textScore.getText().toString());
            playerNames[i] = playerEditText.getText().toString();
        }

        outState.putIntArray(KEY_PLAYER_SCORES, playerScores);
        outState.putStringArray(KEY_PLAYER_NAMES, playerNames);
        outState.putInt(KEY_CURRENT_INDEX, currentClickableButtonIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        LinearLayout containerPlayers = findViewById(R.id.containerPlayers);
        int playerCount = containerPlayers.getChildCount();

        int[] playerScores = savedInstanceState.getIntArray(KEY_PLAYER_SCORES);
        String[] playerNames = savedInstanceState.getStringArray(KEY_PLAYER_NAMES);
        currentClickableButtonIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);

        if (playerScores != null && playerNames != null && playerScores.length == playerNames.length) {
            for (int i = 0; i < playerCount; i++) {
                View playerItemView = containerPlayers.getChildAt(i);
                TextView textScore = playerItemView.findViewById(R.id.scoreText);
                EditText playerEditText = playerItemView.findViewById(R.id.playerName);

                // Safeguard to avoid index out of bounds
                if (i < playerScores.length) {
                    textScore.setText(String.valueOf(playerScores[i]));
                    playerEditText.setText(playerNames[i]);
                }
            }
        }

        // Restore the state of the OK buttons
        for (int i = 0; i < okButtons.size(); i++) {
            okButtons.get(i).setEnabled(i == currentClickableButtonIndex);
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

        btnYes.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(MenuInGame.this, MainMenu.class);
            startActivity(intent);
        });

        btnNo.setOnClickListener(v -> alertDialog.dismiss());

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

        btnRestart.setOnClickListener(v -> {
            for (int i = 0; i < okButtons.size(); i++) {
                View parent = (View) okButtons.get(i).getParent();
                TextView scoreText = parent.findViewById(R.id.scoreText);
                scoreText.setText(getIntent().getStringExtra("regle"));
            }

            // Disable all OK buttons except the first one
            for (int i = 0; i < okButtons.size(); i++) {
                okButtons.get(i).setEnabled(i == 0);
            }

            // Reset the current clickable button index
            currentClickableButtonIndex = 0;

            alertDialog.dismiss();
        });

        btnHome.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(MenuInGame.this, MainMenu.class);
            startActivity(intent);
        });

        alertDialog.show();
    }
}
