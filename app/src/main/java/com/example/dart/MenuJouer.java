package com.example.dart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuJouer extends AppCompatActivity {

    private Button buttonSolo;
    private Button buttonEquipe;
    private Button button310;
    private Button button510;
    private Button buttonPerso;

    private LinearLayout iconContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jouer);

        buttonSolo = findViewById(R.id.buttonSolo);
        buttonEquipe = findViewById(R.id.buttonEquipe);
        button310 = findViewById(R.id.button310);
        button510 = findViewById(R.id.button510);
        buttonPerso = findViewById(R.id.buttonPerso);

        // Initialisation des boutons avec leurs états par défaut
        buttonSolo.setSelected(true); // Solo est sélectionné par défaut
        buttonEquipe.setSelected(false);
        button310.setSelected(true);
        button510.setSelected(false);
        buttonPerso.setSelected(false);

        buttonSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton Solo est cliqué, le sélectionner et désélectionner Équipe
                buttonSolo.setSelected(true);
                buttonEquipe.setSelected(false);

                // Ajoutez ici toute autre logique que vous souhaitez pour ce bouton
            }
        });

        buttonEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton Équipe est cliqué, le sélectionner et désélectionner Solo
                buttonSolo.setSelected(false);
                buttonEquipe.setSelected(true);

                // Ajoutez ici toute autre logique que vous souhaitez pour ce bouton
            }
        });
    }
}