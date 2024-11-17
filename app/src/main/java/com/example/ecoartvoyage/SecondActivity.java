package com.example.ecoartvoyage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SecondActivity extends AppCompatActivity {

    private EditText inputField;
    private ImageView backButton;
    private String correctAnswer;
    private TextView questionText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // Initialize views
        inputField = findViewById(R.id.inputField);
        backButton = findViewById(R.id.backButton);
        questionText = findViewById(R.id.questionText);

        // Load challenge from JSON
        loadChallenge();

        // Set up back button click listener
        backButton.setOnClickListener(v -> finish());

        // Set up EditText clear functionality
        inputField.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (inputField.getRight() - inputField.getCompoundDrawables()[2].getBounds().width())) {
                    inputField.setText("");
                    return true;
                }
            }
            return false;
        });

        // Check answer when user types
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_backspace_24, 0);
                    checkAnswer(s.toString());
                } else {
                    inputField.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadChallenge() {
        try {
            // Read JSON file
            InputStream is = getResources().openRawResource(R.raw.challenges);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse JSON
            JSONObject obj = new JSONObject(json);
            JSONArray challenges = obj.getJSONArray("challenges");

            // For now, just get the first challenge
            JSONObject challenge = challenges.getJSONObject(0);

            // Set the question and correct answer
            questionText.setText(challenge.getString("question"));
            correctAnswer = challenge.getString("answer");

            // You could also set the image here
            // ImageView imageView = findViewById(R.id.recyclingImage);
            // imageView.setImageResource(getResources().getIdentifier(
            //     challenge.getString("image"), "drawable", getPackageName()));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading challenge", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer(String input) {
        if (input.toLowerCase().equals(correctAnswer)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            // Hier können Sie weitere Aktionen hinzufügen, wenn die Antwort richtig ist
        }
    }
}