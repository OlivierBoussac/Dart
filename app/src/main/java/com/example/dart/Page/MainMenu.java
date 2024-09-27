package com.example.dart.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.content.pm.ActivityInfo;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dart.R;

public class MainMenu extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inflater = LayoutInflater.from(this);

        Button buttonJouer = findViewById(R.id.buttonJouer);
        buttonJouer.setOnClickListener(v -> newGame());
    }

    private void newGame() {
        Intent intent = new Intent(MainMenu.this, MenuJouer.class);
        startActivity(intent);
    }
}
