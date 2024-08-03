package com.example.dart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set up the button and its click listener
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupGame();
            }
        });
    }

    private void showPopupGame() {
        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_game, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        // Set up title
        TextView title = dialogView.findViewById(R.id.title);

        // Set up buttons
        Button btnMark301 = dialogView.findViewById(R.id.btnMark301);
        Button btnBack = dialogView.findViewById(R.id.btnBack);

        btnMark301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showPopupNumberPlayer();
            }
        });

        btnBack.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void showPopupNumberPlayer() {
        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nbr_player, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        // Set up buttons
        Button btnBack = dialogView.findViewById(R.id.btnBack);
        Button btnOk = dialogView.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(v -> {
            EditText editTextNumber = dialogView.findViewById(R.id.editTextNumber);
            String numberStr = editTextNumber.getText().toString();
            if (!numberStr.isEmpty()) {
                try {
                    int number = Integer.parseInt(numberStr);

                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("numberOfPlayers", number);
                    startActivity(intent);

                    alertDialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Veuillez entrer un nombre valide", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Veuillez entrer le nombre de joueurs", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
}
