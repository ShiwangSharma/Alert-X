package com.shiwangapp.homepagesih.activities;

import static com.shiwangapp.homepagesih.location.locationCord.getCityNameUsingNetwork;
import static com.shiwangapp.homepagesih.location.locationCord.setLongitudeLatitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shiwangapp.homepagesih.Adapters.SelecteduserActivity;
import com.shiwangapp.homepagesih.Adapters.UserAdapter;
import com.shiwangapp.homepagesih.Adapters.UserModel;
import com.shiwangapp.homepagesih.R;
import com.shiwangapp.homepagesih.location.LatLong;

import java.util.ArrayList;
import java.util.List;

public class ShelterActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String CITY;
    private GoogleMap myMap;

    RecyclerView rvUsers;
    UserAdapter userAdapter;
    List<UserModel> userModelList = new ArrayList<>();

    private Spinner spinnerTextSize, spinnerTextSize1;
    private static final int PERMISSION_CODE = 103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        //getSupportActionBar().setTitle("Shelter");



        spinnerTextSize = findViewById(R.id.spinner1);
        spinnerTextSize1 = findViewById(R.id.spinner2);

        String[] textSizes= getResources().getStringArray(R.array.type_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);

        spinnerTextSize1 = findViewById(R.id.spinner2);

        String[] textSizes2= getResources().getStringArray(R.array.resources_array);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,textSizes2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize1.setAdapter(adapter2);




        CITY = getIntent().getStringExtra("Address");
        //getMarkerPositions();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_shelter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_shelter) {
                return true;
            } else if (itemId == R.id.bottom_tips) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
                finish();
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

//    private void getMarkerPositions(){
//        FirebaseDatabase.getInstance().getReference().child("contactForm").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    ll.clear();
//                    name.clear();
//
//                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                        LatLong i = snapshot1.getValue(LatLong.class);
//                        String s = i.getName();
//                        LatLng t = new LatLng(Double.parseDouble(i.getLatitude()), Double.parseDouble((i.getLongitude())));
//
//                        ll.add(t);
//                        name.add(s);
//                        LatLng l1 = new LatLng(12.960111,80.057375);
//                        Log.i("Pass1", "onDataChange: "+ll+name+l1);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ShelterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                Log.i("Fail1", "onCancelled: "+error.toString());
//            }
//        });
//    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }else {
            client.getLastLocation().addOnSuccessListener(location -> {
                LatLng current_loc = new LatLng(location.getLatitude(), location.getLongitude());
                myMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(current_loc).title("You are here").draggable(true));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(current_loc));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_loc, 7));


            });
        }

        ArrayList<LatLng> ll = new ArrayList<>();
        //ArrayList<String> name = new ArrayList<>();



        FirebaseDatabase.getInstance().getReference().child("contactForm").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ll.clear();
                    //name.clear();

                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        LatLong i = snapshot1.getValue(LatLong.class);
                        String s = i.getName();
                        LatLng t = new LatLng(Double.parseDouble(i.getLatitude()), Double.parseDouble((i.getLongitude())));
                        myMap.addMarker(new MarkerOptions().position(t).title(s));
                        Log.i("NewMarker", "onDataChange: "+t+s);
                        ll.add(t);
                        //name.add(s);
                        LatLng l1 = new LatLng(12.960111,80.057375);
                        Log.i("Pass1", "onDataChange: "+ll.size());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShelterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.i("Fail1", "onCancelled: "+error.toString());
            }
        });



        LatLng sharda = new LatLng(28.4731, 77.4829);
        LatLng niet = new LatLng(28.4629, 77.4907);
        LatLng fortis = new LatLng(28.6187, 77.3726);
        LatLng king_inst = new LatLng(13.012442, 80.217615);
        LatLng Svcet = new LatLng(13.2718,79.1187);
        LatLng rvs_hosp = new LatLng(13.2718, 79.11986);

        myMap.addMarker(new MarkerOptions().position(sharda).title("Sharda Hospital"));
        myMap.addMarker(new MarkerOptions().position(niet).title("NIET"));
        myMap.addMarker(new MarkerOptions().position(fortis).title("Fortis Hospital"));
        myMap.addMarker(new MarkerOptions().position(king_inst).title("King Institute of Preventive Medicine & Research"));
        myMap.addMarker(new MarkerOptions().position(Svcet).title("Sri Venkateswara College of Engineering Technology ,Chittoor, Andhra Pradesh, India"));
        myMap.addMarker(new MarkerOptions().position(rvs_hosp).title("RVS Hospitals, Chittoor, Andhra Pradesh, India"));

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Toast.makeText(ShelterActivity.this, marker.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //myMap.moveCamera(CameraUpdateFactory.newLatLng(sharda));
        //myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sharda, 16f));

    }





}