package com.example.dart.Page;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dart.R;
import com.example.dart.object.AllStatistique;
import com.example.dart.repository.DBHandler;

import java.util.List;

public class Statistique extends AppCompatActivity {

    private LayoutInflater inflater;
    private DBHandler dbHandler;

    private ImageButton buttonHome;
    private TableLayout tableLayout; // Ajoutez cette variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistique);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inflater = LayoutInflater.from(this);
        dbHandler = new DBHandler(this);

        initialize();
        loadData(); // Chargez les données lors de la création

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistique.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        buttonHome = findViewById(R.id.homeIcon); // Assurez-vous que l'ID est correct
        tableLayout = findViewById(R.id.tableLayout); // Liez le TableLayout
    }

    private void loadData() {
        List<AllStatistique> statistiques = dbHandler.getStatistique();

        for (AllStatistique statistique : statistiques) {
            TableRow tableRow = new TableRow(this);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(statistique.getId());
            nameTextView.setPadding(8, 8, 8, 8);
            nameTextView.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f));
            tableRow.addView(nameTextView);

            TextView lastScoreTextView = new TextView(this);
            lastScoreTextView.setText(String.valueOf(statistique.getWinner_name()));
            lastScoreTextView.setPadding(8, 8, 8, 8);
            lastScoreTextView.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f));
            tableRow.addView(lastScoreTextView);

            // Ajouter la ligne de données au tableau
            tableLayout.addView(tableRow);
        }
    }
}
