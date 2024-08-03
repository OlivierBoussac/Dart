package com.example.dart;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Retrieve the number of players from the intent
        int numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 0);

        // Find the LinearLayout container for players
        LinearLayout containerPlayers = findViewById(R.id.containerPlayers);

        // Define the margin value
        int marginInDp = 16;
        final float scale = getResources().getDisplayMetrics().density;
        int marginInPx = (int) (marginInDp * scale + 0.5f);

        // Dynamically add views for each player
        for (int i = 1; i <= numberOfPlayers; i++) {
            // Create a horizontal LinearLayout for each player
            LinearLayout playerLayout = new LinearLayout(this);
            playerLayout.setOrientation(LinearLayout.HORIZONTAL);
            playerLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);

            // Create and set up the player TextView
            TextView playerTextView = new TextView(this);
            playerTextView.setText("Joueur " + i);
            playerTextView.setPadding(16, 16, 16, 16);
            playerTextView.setTextSize(18);

            // Create and set up the 301 TextView
            TextView text301 = new TextView(this);
            text301.setText("301");
            text301.setPadding(16, 16, 16, 16);
            text301.setTextSize(18);

            // Create and set up the EditText
            EditText editText = new EditText(this);
            editText.setHint("Entrez une valeur");
            editText.setPadding(16, 16, 16, 16);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

            // Create and set up the OK Button
            Button btnOk = new Button(this);
            btnOk.setText("OK");
            btnOk.setPadding(16, 16, 16, 16);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredValueStr = editText.getText().toString();

                    if (!enteredValueStr.isEmpty()) {
                        try {
                            int currentValue = Integer.parseInt(text301.getText().toString());
                            int enteredValue = Integer.parseInt(enteredValueStr);

                            // Perform the subtraction
                            int newValue = currentValue - enteredValue;

                            // Ensure the new value is valid
                            if (newValue >= 0) {
                                text301.setText(String.valueOf(newValue));
                            } else {
                                Toast.makeText(MainActivity2.this, "Le score ne peut pas être inférieur à 0.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity2.this, "Valeur entrée invalide.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity2.this, "Veuillez entrer une valeur.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Add views to the player layout
            playerLayout.addView(playerTextView);
            playerLayout.addView(text301);
            playerLayout.addView(editText);
            playerLayout.addView(btnOk);

            // Set up the layout parameters for the player layout
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, marginInPx, 0, 0); // Add top margin
            playerLayout.setLayoutParams(params);

            // Add the player layout to the container
            containerPlayers.addView(playerLayout);
        }

        // Set up the Home button
        Button buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the MainActivity
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
