package com.example.dart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private LinearLayout containerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        inflater = LayoutInflater.from(this);
        containerMenu = findViewById(R.id.containerMenu);

        // Lancer le processus de démarrage du jeu
        newGame();
    }

    private void newGame() {
        // Inflate the player item layout
        View menuView = inflater.inflate(R.layout.new_game, containerMenu, false);
        // Set up the button and its click listener
        Button button = menuView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerMenu.removeView(menuView);
                selectGame();
            }
        });
        containerMenu.addView(menuView);
    }

    private void selectGame() {
        View menuSelectGameView = inflater.inflate(R.layout.selection_game_mode, containerMenu, false);
        Button button301 = menuSelectGameView.findViewById(R.id.btnMode301);
        button301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerMenu.removeView(menuSelectGameView);
                SelectNbrPlayer("301");
            }
        });
        Button button501 = menuSelectGameView.findViewById(R.id.btnMode501);
        button501.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerMenu.removeView(menuSelectGameView);
                SelectNbrPlayer("501");
            }
        });
        Button buttonBack = menuSelectGameView.findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerMenu.removeView(menuSelectGameView);
                newGame();
            }
        });
        containerMenu.addView(menuSelectGameView);
    }

    private void SelectNbrPlayer(String regle) {
        View menuSelectNbrPlayerView = inflater.inflate(R.layout.selection_nbr_player, containerMenu, false);
        Button btnBack = menuSelectNbrPlayerView.findViewById(R.id.btnBack);
        Button btnOk = menuSelectNbrPlayerView.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            EditText editTextNumber = menuSelectNbrPlayerView.findViewById(R.id.editTextNumber);
            String numberStr = editTextNumber.getText().toString();
            if (!numberStr.isEmpty()) {
                try {
                    int number = Integer.parseInt(numberStr);

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("numberOfPlayers", number);
                    intent.putExtra("regle", regle);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer un nombre valide", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Veuillez entrer le nombre de joueurs", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            containerMenu.removeView(menuSelectNbrPlayerView);
            selectGame();
        });
        containerMenu.addView(menuSelectNbrPlayerView);
    }
}
