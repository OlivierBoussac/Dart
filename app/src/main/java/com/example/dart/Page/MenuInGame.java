package com.example.dart.Page;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dart.R;
import com.example.dart.object.ParamGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MenuInGame extends AppCompatActivity {

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonSend, buttonBack;
    private ImageButton buttonHome;
    private TextView textViewScore;
    private String ScoreInput = "";
    private ImageView icDelete;
    private ArrayList<Integer> scoreList = new ArrayList<>();

    private ParamGame paramGame;

    private int currentPlayerToModify = 0;

    private TextView currentPlayer, currentScore, Player1, Score1, Player2, Score2, Player3, Score3;

    private int playerToStart = 0;

    // Stack to keep track of previous scores for undo functionality
    private Stack<ScoreState> previousScores = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_in_game);

        initialize();

        paramGame = (ParamGame) getIntent().getSerializableExtra("paramGame");

        startGameLoadData();

        setButtonListeners();

        icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScoreInput.length() > 0) {
                    ScoreInput = ScoreInput.substring(0, ScoreInput.length() - 1);
                    textViewScore.setText(ScoreInput);
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countScore();
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupLeave();
            }
        });

        // Add OnClickListener for undo button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastScore();
            }
        });

        textViewScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (s.length() > 2) {
                    disableNumberButtons();
                } else {
                    enableNumberButtons();
                }
            }
        });
    }

    private void initialize() {
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        buttonSend = findViewById(R.id.buttonSend);
        buttonBack = findViewById(R.id.buttonBack); // Ensure buttonBack is defined in XML

        textViewScore = findViewById(R.id.textViewScore);

        icDelete = findViewById(R.id.icDelete);

        buttonHome = findViewById(R.id.buttonHome);

        currentPlayer = findViewById(R.id.textViewCurrentPlayer);
        currentScore = findViewById(R.id.textViewCurrentScore);

        Player1 = findViewById(R.id.textViewPlayer1);
        Score1 = findViewById(R.id.textViewScore1);
        Player2 = findViewById(R.id.textViewPlayer2);
        Score2 = findViewById(R.id.textViewScore2);
        Player3 = findViewById(R.id.textViewPlayer3);
        Score3 = findViewById(R.id.textViewScore3);
    }

    private void setButtonListeners() {
        View.OnClickListener scoreButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                ScoreInput += clickedButton.getText().toString();
                textViewScore.setText(ScoreInput);
            }
        };

        button0.setOnClickListener(scoreButtonListener);
        button1.setOnClickListener(scoreButtonListener);
        button2.setOnClickListener(scoreButtonListener);
        button3.setOnClickListener(scoreButtonListener);
        button4.setOnClickListener(scoreButtonListener);
        button5.setOnClickListener(scoreButtonListener);
        button6.setOnClickListener(scoreButtonListener);
        button7.setOnClickListener(scoreButtonListener);
        button8.setOnClickListener(scoreButtonListener);
        button9.setOnClickListener(scoreButtonListener);
    }

    private void startGameLoadData() {
        for (int i = 0; i < paramGame.getJoueur().size(); i++) {
            scoreList.add(paramGame.getScore());
        }

        currentPlayer.setText(paramGame.getJoueur().get(0).getName());
        currentScore.setText(String.valueOf(scoreList.get(0)));

        if (paramGame.getJoueur().size() >= 2) {
            Player1.setText(paramGame.getJoueur().get(1).getName());
            Score1.setText(String.valueOf(scoreList.get(1)));
        } else {
            Player1.setText("");
            Score1.setText("");
        }

        if (paramGame.getJoueur().size() >= 3) {
            Player2.setText(paramGame.getJoueur().get(2).getName());
            Score2.setText(String.valueOf(scoreList.get(2)));
        } else {
            Player2.setText("");
            Score2.setText("");
        }

        if (paramGame.getJoueur().size() >= 4) {
            Player3.setText(paramGame.getJoueur().get(3).getName());
            Score3.setText(String.valueOf(scoreList.get(3)));
        } else {
            Player3.setText("");
            Score3.setText("");
        }
    }

    private void disableNumberButtons() {
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }

    private void enableNumberButtons() {
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
    }

    private void countScore() {
        try {
            int enteredScore = Integer.parseInt(ScoreInput);

            if (enteredScore > 180) {
                Toast.makeText(MenuInGame.this, "Le score ne peut pas dépasser 180", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save current state before modifying the score
            previousScores.push(new ScoreState(currentPlayerToModify, scoreList.get(currentPlayerToModify)));

            scoreList.set(currentPlayerToModify, scoreList.get(currentPlayerToModify) - enteredScore);

            rollOfPlayer();

        } catch (NumberFormatException e) {
            Toast.makeText(MenuInGame.this, "Veuillez entrer un score valide", Toast.LENGTH_SHORT).show();
        }
    }

    private void rollOfPlayer() {
        if (scoreList.get(currentPlayerToModify) == 0) {
            showPopupWin();
        }

        currentPlayerToModify++;

        if (currentPlayerToModify >= paramGame.getJoueur().size()) {
            currentPlayerToModify = 0;
        }

        updatePlayerDisplay();
    }

    private void updatePlayerDisplay() {
        currentPlayer.setText(paramGame.getJoueur().get(currentPlayerToModify).getName());
        currentScore.setText(String.valueOf(scoreList.get(currentPlayerToModify)));

        if (paramGame.getJoueur().size() >= 2) {
            Player1.setText(paramGame.getJoueur().get((currentPlayerToModify + 1) % paramGame.getJoueur().size()).getName());
            Score1.setText(String.valueOf(scoreList.get((currentPlayerToModify + 1) % paramGame.getJoueur().size())));
        } else {
            Player1.setText("");
            Score1.setText("");
        }

        if (paramGame.getJoueur().size() >= 3) {
            Player2.setText(paramGame.getJoueur().get((currentPlayerToModify + 2) % paramGame.getJoueur().size()).getName());
            Score2.setText(String.valueOf(scoreList.get((currentPlayerToModify + 2) % paramGame.getJoueur().size())));
        } else {
            Player2.setText("");
            Score2.setText("");
        }

        if (paramGame.getJoueur().size() >= 4) {
            Player3.setText(paramGame.getJoueur().get((currentPlayerToModify + 3) % paramGame.getJoueur().size()).getName());
            Score3.setText(String.valueOf(scoreList.get((currentPlayerToModify + 3) % paramGame.getJoueur().size())));
        } else {
            Player3.setText("");
            Score3.setText("");
        }

        ScoreInput = "";
        textViewScore.setText(ScoreInput);
    }

    private void undoLastScore() {
        if (!previousScores.isEmpty()) {
            ScoreState lastScoreState = previousScores.pop();
            scoreList.set(lastScoreState.playerIndex, lastScoreState.score);
            currentPlayerToModify = lastScoreState.playerIndex;
            updatePlayerDisplay();
        } else {
            Toast.makeText(MenuInGame.this, "Aucun score à annuler", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupWin() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_winner, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        Button btnRestart = dialogView.findViewById(R.id.btnRestart);
        Button btnHome = dialogView.findViewById(R.id.btnHome);
        TextView tvNamePlayer = dialogView.findViewById(R.id.tvWinnerName);

        tvNamePlayer.setText("Le gagnant est " + paramGame.getJoueur().get(currentPlayerToModify).getName());

        btnHome.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(MenuInGame.this, MainMenu.class);
            startActivity(intent);
        });

        btnRestart.setOnClickListener(v -> {
            alertDialog.dismiss();
            resetGame();
        });
        alertDialog.show();
    }

    private void resetGame() {
        scoreList.clear();
        playerToStart += 1;
        currentPlayerToModify = playerToStart;

        if (currentPlayerToModify >= paramGame.getJoueur().size()) {
            currentPlayerToModify = 0;
        }

        for (int i = 0; i < paramGame.getJoueur().size(); i++) {
            scoreList.add(paramGame.getScore());
        }

        updatePlayerDisplay();
        previousScores.clear(); // Clear the undo stack when restarting
    }

    private void showPopupLeave() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_validation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

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

    // Helper class to save score state for undo functionality
    private static class ScoreState {
        int playerIndex;
        int score;

        ScoreState(int playerIndex, int score) {
            this.playerIndex = playerIndex;
            this.score = score;
        }
    }
}
