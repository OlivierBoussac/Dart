package com.example.dart.Page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dart.R;
import com.example.dart.object.Joueur;
import com.example.dart.object.ParamGame;

import java.util.ArrayList;

public class MenuJouer extends AppCompatActivity {

    private Button buttonSolo;
    private Button buttonEquipe;
    private Button button310;
    private Button button510;
    private Button buttonPerso;
    private CheckBox checkBoxDebutSimple;
    private CheckBox checkBoxDebutDouble;
    private CheckBox checkBoxDebutMaster;
    private CheckBox checkBoxFinSimple;
    private CheckBox checkBoxFinDouble;
    private CheckBox checkBoxFinMaster;

    private ParamGame paramGame;

    private ArrayList<Joueur> joueur;

    private GridLayout iconContainer;
    private ImageView iconAddPerson;
    private int playerCount = 0;
    private static final int MAX_PLAYERS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jouer);

        buttonSolo = findViewById(R.id.buttonSolo);
        buttonEquipe = findViewById(R.id.buttonEquipe);
        button310 = findViewById(R.id.button310);
        button510 = findViewById(R.id.button510);
        buttonPerso = findViewById(R.id.buttonPerso);
        checkBoxDebutSimple = findViewById(R.id.checkBoxDebutSimple);
        checkBoxDebutDouble = findViewById(R.id.checkBoxDebutDouble);
        checkBoxDebutMaster = findViewById(R.id.checkBoxDebutMaster);
        checkBoxFinSimple = findViewById(R.id.checkBoxFinSimple);
        checkBoxFinDouble = findViewById(R.id.checkBoxFinDouble);
        checkBoxFinMaster = findViewById(R.id.checkBoxFinMaster);

        paramGame = new ParamGame();

        // Initialiser le conteneur d'icônes (GridLayout) et le bouton d'ajout de joueur
        iconContainer = findViewById(R.id.iconContainer);

        // Initialisation des boutons avec leurs états par défaut
        buttonSolo.setSelected(true); // Solo est sélectionné par défaut
        buttonEquipe.setSelected(false);
        button310.setSelected(true);
        button510.setSelected(false);
        buttonPerso.setSelected(false);
        checkBoxDebutSimple.setChecked(true);
        checkBoxDebutDouble.setChecked(false);
        checkBoxDebutMaster.setChecked(false);
        checkBoxFinSimple.setChecked(true);
        checkBoxFinDouble.setChecked(false);
        checkBoxFinMaster.setChecked(false);

        // Ajouter l'icône "Ajouter une personne" au démarrage
        addIconAddPerson();

        // Ajouter automatiquement un joueur nommé "Joueur 1"
        addDefaultPlayer();

        iconAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerCount < MAX_PLAYERS) {
                    addPlayerIcon(null);
                } else {
                    Toast.makeText(MenuJouer.this, "Maximum de joueurs atteint", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSolo.setSelected(true);
                buttonEquipe.setSelected(false);
                paramGame.setEquipe(false);
            }
        });

        buttonEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSolo.setSelected(false);
                buttonEquipe.setSelected(true);
                paramGame.setEquipe(true);
            }
        });

        button310.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button310.setSelected(true);
                button510.setSelected(false);
                buttonPerso.setSelected(false);
                paramGame.setScore(310);
            }
        });

        button510.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button310.setSelected(false);
                button510.setSelected(true);
                buttonPerso.setSelected(false);
                paramGame.setScore(510);
            }
        });

        buttonPerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button310.setSelected(false);
                button510.setSelected(false);
                buttonPerso.setSelected(true);

                showCustomDialog();
            }
        });

        checkBoxDebutSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDebutSimple.setChecked(true);
                checkBoxDebutDouble.setChecked(false);
                checkBoxDebutMaster.setChecked(false);
                paramGame.setDebut("Simple");
            }
        });

        checkBoxDebutDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDebutSimple.setChecked(false);
                checkBoxDebutDouble.setChecked(true);
                checkBoxDebutMaster.setChecked(false);
                paramGame.setDebut("Double");
            }
        });

        checkBoxDebutMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDebutSimple.setChecked(false);
                checkBoxDebutDouble.setChecked(false);
                checkBoxDebutMaster.setChecked(true);
                paramGame.setDebut("Master");
            }
        });

        checkBoxFinSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxFinSimple.setChecked(true);
                checkBoxFinDouble.setChecked(false);
                checkBoxFinMaster.setChecked(false);
                paramGame.setFin("Simple");
            }
        });

        checkBoxFinDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxFinSimple.setChecked(false);
                checkBoxFinDouble.setChecked(true);
                checkBoxFinMaster.setChecked(false);
                paramGame.setFin("Double");
            }
        });

        checkBoxFinMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxFinSimple.setChecked(false);
                checkBoxFinDouble.setChecked(false);
                checkBoxFinMaster.setChecked(true);
                paramGame.setFin("Master");
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuJouer.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_perso_score, null);
        builder.setView(dialogView);

        // Références aux vues du dialog
        EditText etScoreInput = dialogView.findViewById(R.id.etScoreInput);
        Button btnValid = dialogView.findViewById(R.id.btnValide);
        Button btnCancel = dialogView.findViewById(R.id.btnRetour);

        AlertDialog alertDialog = builder.create();

        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scoreInput = etScoreInput.getText().toString().trim();

                if (!TextUtils.isEmpty(scoreInput)) {
                    // Traitez le score saisi (par exemple, en l'enregistrant dans une variable ou un objet)
                    Toast.makeText(MenuJouer.this, "Score sélectionné : " + scoreInput, Toast.LENGTH_SHORT).show();
                    paramGame.setScore(Integer.parseInt(scoreInput));
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(MenuJouer.this, "Veuillez entrer un score", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    // Méthode pour ajouter un joueur par défaut nommé "Joueur 1"
    private void addDefaultPlayer() {
        addPlayerIconToGrid("Joueur 1");
    }

    // Méthode pour ajouter l'icône "Ajouter une personne" au début
    private void addIconAddPerson() {
        iconAddPerson = new ImageView(this);
        iconAddPerson.setImageResource(R.drawable.ic_add_person);
        iconAddPerson.setId(View.generateViewId());

        GridLayout.LayoutParams addPersonLayoutParams = new GridLayout.LayoutParams();
        addPersonLayoutParams.width = 150;
        addPersonLayoutParams.height = 150;
        addPersonLayoutParams.rowSpec = GridLayout.spec(0);
        addPersonLayoutParams.columnSpec = GridLayout.spec(playerCount);

        addPersonLayoutParams.setMargins(24, 16, 24, 16);
        addPersonLayoutParams.setGravity(Gravity.CENTER);

        iconAddPerson.setLayoutParams(addPersonLayoutParams);
        iconContainer.addView(iconAddPerson);
    }

    // Méthode pour ajouter une icône de joueur
    private void addPlayerIcon(String initialPlayerName) {
        if (playerCount >= MAX_PLAYERS) {
            Toast.makeText(MenuJouer.this, "Maximum de joueurs atteint", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuJouer.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_player, null);
        builder.setView(dialogView);

        EditText editTextPlayerName = dialogView.findViewById(R.id.etPlayerName);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnOK);

        // Si un nom initial est fourni, le définir dans le champ d'édition
        if (!TextUtils.isEmpty(initialPlayerName)) {
            editTextPlayerName.setText(initialPlayerName);
        }

        AlertDialog alertDialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = editTextPlayerName.getText().toString().trim();

                if (!TextUtils.isEmpty(playerName)) {
                    addPlayerIconToGrid(playerName);

                    joueur.add(new Joueur(playerName));

                    paramGame.setJoueur(joueur);

                    alertDialog.dismiss();
                } else {
                    Toast.makeText(MenuJouer.this, "Veuillez entrer un nom", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    // Méthode pour ajouter une icône de joueur au conteneur
    private void addPlayerIconToGrid(String playerName) {
        if (playerCount >= MAX_PLAYERS) {
            Toast.makeText(MenuJouer.this, "Maximum de joueurs atteint", Toast.LENGTH_SHORT).show();
            return;
        }

        LinearLayout playerLayout = new LinearLayout(this);
        playerLayout.setOrientation(LinearLayout.VERTICAL);
        playerLayout.setGravity(Gravity.CENTER);

        ImageView playerIcon = new ImageView(this);
        playerIcon.setImageResource(R.drawable.ic_person);  // Remplace par ton icône de joueur
        playerIcon.setLayoutParams(new LinearLayout.LayoutParams(150, 150));

        TextView playerNameTextView = new TextView(this);
        playerNameTextView.setText(playerName);
        playerNameTextView.setGravity(Gravity.CENTER);

        playerLayout.addView(playerIcon);
        playerLayout.addView(playerNameTextView);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.rowSpec = GridLayout.spec(0);
        layoutParams.columnSpec = GridLayout.spec(playerCount);

        layoutParams.setMargins(24, 16, 24, 16);
        layoutParams.setGravity(Gravity.CENTER);

        playerLayout.setLayoutParams(layoutParams);

        // Ajouter un écouteur de clic pour ouvrir la boîte de dialogue de modification
        playerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditPlayerDialog(playerLayout, playerNameTextView);
            }
        });

        iconContainer.addView(playerLayout, playerCount);
        playerCount++;

        reorganizePlayerIcons();
    }

    // Méthode pour ouvrir une boîte de dialogue pour modifier le nom du joueur ou le supprimer
    private void openEditPlayerDialog(LinearLayout playerLayout, TextView playerNameTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuJouer.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_player, null);
        builder.setView(dialogView);

        EditText editTextPlayerName = dialogView.findViewById(R.id.etPlayerName);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnOK);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete); // Nouveau bouton pour supprimer le joueur

        // Remplir le champ d'édition avec le nom actuel du joueur
        editTextPlayerName.setText(playerNameTextView.getText().toString().trim());

        AlertDialog alertDialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPlayerName = editTextPlayerName.getText().toString().trim();

                if (!TextUtils.isEmpty(newPlayerName)) {
                    playerNameTextView.setText(newPlayerName);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(MenuJouer.this, "Veuillez entrer un nom", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconContainer.removeView(playerLayout);
                playerCount--;
                reorganizePlayerIcons();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    // Méthode pour réorganiser les icônes des joueurs dans le GridLayout
    private void reorganizePlayerIcons() {
        int columnCount = iconContainer.getColumnCount();
        int rowCount = iconContainer.getRowCount();

        for (int i = 0; i < iconContainer.getChildCount(); i++) {
            View child = iconContainer.getChildAt(i);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) child.getLayoutParams();
            layoutParams.rowSpec = GridLayout.spec(i / columnCount);
            layoutParams.columnSpec = GridLayout.spec(i % columnCount);
            child.setLayoutParams(layoutParams);
        }
    }
}
