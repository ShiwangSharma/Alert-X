package com.shiwangapp.homepagesih.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shiwangapp.homepagesih.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getSupportActionBar().setTitle("Profile");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_shelter) {
                startActivity(new Intent(getApplicationContext(), ShelterActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_tips) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                return true;
            } else if (itemId == R.id.bottom_placeholder) {
                startActivity(new Intent(getApplicationContext(), SOSActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
}