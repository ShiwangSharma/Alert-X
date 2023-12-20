package com.shiwangapp.homepagesih.Adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shiwangapp.homepagesih.R;

public class SelecteduserActivity extends AppCompatActivity {

    TextView tvSelectedUserName;
    UserModel userModel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteduser);
        tvSelectedUserName = findViewById(R.id.tvSelectedUserName);
        intent = getIntent();

        if(intent !=  null){
            userModel = (UserModel) intent.getSerializableExtra("data");
            String userName = userModel.getFirstname() +""+ userModel.getLastname();
            tvSelectedUserName.setText(userName);
        }
    }
}