package com.shiwangapp.homepagesih.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shiwangapp.homepagesih.R;
import com.shiwangapp.homepagesih.location.LatLong;

import java.util.ArrayList;

public class ArrayActivity extends AppCompatActivity {

    Button showValue;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array);

        showValue = findViewById(R.id.show);
        listView = findViewById(R.id.list);

        showValue.setOnClickListener(view -> {
            ArrayList<LatLng> a = new ArrayList<>();
            ArrayAdapter adapter = new ArrayAdapter<LatLng>(ArrayActivity.this, R.layout.list_item, a);
            listView.setAdapter(adapter);

            FirebaseDatabase.getInstance().getReference().child("contactForm").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        a.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            LatLong i = snapshot1.getValue(LatLong.class);
                            LatLng t = new LatLng(Double.parseDouble(i.getLatitude()), Double.parseDouble((i.getLongitude())));
                            //String t = i.getLatitude()+","+i.getLongitude();
                            a.add(t);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ArrayActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Log.i("Fail1", "onCancelled: "+error.toString());
                }
            });
        });

    }
}