package com.shiwangapp.homepagesih.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.shiwangapp.homepagesih.R;
import com.shiwangapp.homepagesih.activities.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    MaterialButton signUpBtn, guestBtn, signInBtn;
    EditText signUpNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn = findViewById(R.id.signin_button);
        guestBtn = findViewById(R.id.guestBtn);
        signUpNum = findViewById(R.id.signup_num);

        signUpBtn.setOnClickListener(v -> {
            if (signUpNum.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter Number", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "You are signed Up", Toast.LENGTH_SHORT).show();
            finish();
        });

        guestBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "You are continuing as guest", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}