package com.example.dart.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.Button;
import android.content.pm.ActivityInfo;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dart.R;
import com.example.dart.object.StatistiqueInGame;
import com.example.dart.repository.DBHandler;

public class MainMenu extends AppCompatActivity {

    private Button buttonJouer;
    private Button buttonHistorique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonJouer = findViewById(R.id.buttonJouer);
        buttonHistorique = findViewById(R.id.buttonHistorique);

        buttonJouer.setOnClickListener(v -> newGame());
//        buttonHistorique.setOnClickListener(v -> goToStatistique());
    }

    private void newGame() {
        Intent intent = new Intent(MainMenu.this, MenuJouer.class);
        startActivity(intent);
    }

    private void goToStatistique() {
        Intent intent = new Intent(MainMenu.this, Statistique.class);
        startActivity(intent);
    }
}
