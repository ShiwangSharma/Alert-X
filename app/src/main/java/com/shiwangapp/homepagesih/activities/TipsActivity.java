package com.shiwangapp.homepagesih.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shiwangapp.homepagesih.R;
import com.shiwangapp.homepagesih.activities.MainActivity;
import com.shiwangapp.homepagesih.activities.ProfileActivity;
import com.shiwangapp.homepagesih.activities.SOSActivity;
import com.shiwangapp.homepagesih.activities.ShelterActivity;

public class TipsActivity extends AppCompatActivity {

    WebView wbView;

    @Override
    public void onBackPressed() {
        if (wbView.canGoBack()){
            wbView.goBack();
        }else {
            super.onBackPressed();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        //getSupportActionBar().setTitle("Tips");

        wbView = findViewById(R.id.wbView);
        wbView.setWebViewClient(new WebViewClient());
        wbView.loadUrl("https://webriy.com/alertx/members");

        WebSettings webSettings = wbView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_tips);

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
                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
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