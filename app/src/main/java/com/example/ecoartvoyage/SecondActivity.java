package com.example.ecoartvoyage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SecondActivity extends AppCompatActivity {

    private EditText inputField;
    private ImageView backButton;
    private String correctAnswer;
    private TextView questionText;

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

        // Set up enter key listener
        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    checkAnswerAndProceed(inputField.getText().toString());
                    return true;
                }
                return false;
            }
        });

        // Show/hide clear button based on text content
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_backspace_24, 0);
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
            InputStream is = getResources().openRawResource(R.raw.challenges);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject obj = new JSONObject(json);
            JSONArray challenges = obj.getJSONArray("challenges");
            JSONObject challenge = challenges.getJSONObject(0);

            questionText.setText(challenge.getString("question"));
            correctAnswer = challenge.getString("answer");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading challenge", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswerAndProceed(String input) {
        if (input.toLowerCase().trim().equals(correctAnswer)) {

            Intent intent = new Intent(SecondActivity.this, SuccessActivity.class);
            startActivity(intent);
            finish();
        } else {

            View rootView = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_LONG);


            View customSnackView = getLayoutInflater().inflate(R.layout.custom_error_snackbar, null);
            @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();


            snackbarLayout.setPadding(0, 0, 0, 0);


            snackbarLayout.addView(customSnackView, 0);


            snackbarLayout.setBackgroundColor(Color.TRANSPARENT);

            ViewGroup.LayoutParams params = snackbarLayout.getLayoutParams();
            if (params instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
            }

            snackbar.show();
            inputField.setText("");
        }
    }
}