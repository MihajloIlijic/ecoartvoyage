package com.example.ecoartvoyage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText inputField;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // Initialize views
        inputField = findViewById(R.id.inputField);
        backButton = findViewById(R.id.backButton);

        // Set up back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will close the activity and return to the previous one
            }
        });

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
}