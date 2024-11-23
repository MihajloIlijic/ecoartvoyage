package com.example.ecoartvoyage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CompletedChallengesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_challenges);

        // Back button setup
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Bottom Navigation setup
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_profile); // Set Profile as selected

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                // Don't create new activity if we're already on it
                if (itemId == R.id.nav_profile) {
                    return true;
                }

                Intent intent = null;
                if (itemId == R.id.nav_challenges) {
                    intent = new Intent(CompletedChallengesActivity.this, AvailableChallengesActivity.class);
                } else if (itemId == R.id.nav_categories) {
                    intent = new Intent(CompletedChallengesActivity.this, KnowledgeAreaActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish(); // Close current activity to prevent stack build-up
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure correct item is selected when returning to this activity
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_profile);
        }
    }
}