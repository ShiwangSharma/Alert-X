package com.shiwangapp.homepagesih.activities;

import static com.shiwangapp.homepagesih.location.locationCord.getCityNameUsingNetwork;
import static com.shiwangapp.homepagesih.location.locationCord.setLongitudeLatitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.shiwangapp.homepagesih.Adapters.NewsAdapter;
import com.shiwangapp.homepagesih.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView current_location,tvPressure,tvWind, tvTemp;
    String CITY;
    MaterialButton locateBtn;
    FloatingActionButton fab;
    FirebaseDatabase db;
    RecyclerView newsRecycler;
    List<Article> articleList = new ArrayList<>();
    NewsAdapter adapter;
    LinearProgressIndicator progressIndicator;
    CardView find_shelter_card, chat_card, profile_card;
    private final String news_api = "1bd958290349424b9e4850b6cd09e6cb";
    private static final int PERMISSION_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle("Home");
        fab = findViewById(R.id.fab);
        newsRecycler = findViewById(R.id.news_recycler);
        progressIndicator = findViewById(R.id.progress_bar);
        tvPressure = findViewById(R.id.tvPressure);
        tvWind = findViewById(R.id.tvWind);
        tvTemp = findViewById(R.id.tvTemp);
        assign_views();
        getPermission();
        getNews();

        setUpRecyclerView();
        getCurrentLocation();


    }

    private void setUpRecyclerView() {
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(articleList);
        newsRecycler.setAdapter(adapter);
    }

    private void changeInProgress(boolean show) {
        if (show) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void getNews() {
        NewsApiClient newsApiClient = new NewsApiClient("1bd958290349424b9e4850b6cd09e6cb");
        newsApiClient.getTopHeadlines(new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category("general")
                        .country("in")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        runOnUiThread(() -> {
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Got FAILURE", throwable.getMessage());
                    }
                });
    }

    private void getPermission() {
    }

    private void getTodayWeatherInfo(String name) {
        String key = "https://api.weatherapi.com/v1/forecast.json?key=fe9c50d155d54f99933180501230707&q=" + name + "&days=3&aqi=no&alerts=no";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, key, null, response -> {
            try {
                String pressure= response.getJSONObject("current").getString("pressure_mb");
                String wind= response.getJSONObject("current").getString("wind_kph");
                String temperature= response.getJSONObject("current").getString("temp_c");

                tvTemp.setText(temperature+"°C");
                tvPressure.setText(pressure+"mb");
                tvWind.setText(wind+"KM");






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }





    private void assign_views() {
        current_location = findViewById(R.id.current_location);
        locateBtn = findViewById(R.id.locate);
        find_shelter_card = findViewById(R.id.find_shelter_card);
        chat_card = findViewById(R.id.chat_card);
        profile_card = findViewById(R.id.profile_card);

        find_shelter_card.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ShelterActivity.class));
            finish();
        });
        profile_card.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        });
        chat_card.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TipsActivity.class));
            finish();
        });
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SOSActivity.class));
            finish();
        });
        locateBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), ShelterActivity.class);
            intent1.putExtra("Address", CITY);
            startActivity(intent1);
            finish();
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                return true;
            } else if (itemId == R.id.bottom_shelter) {
                Intent intent = new Intent(getApplicationContext(), ShelterActivity.class);
                intent.putExtra("Address", CITY);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.bottom_tips) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        } else {
            client.getLastLocation().addOnSuccessListener(location -> {
                setLongitudeLatitude(location);
                CITY = getCityNameUsingNetwork(this, location);
                current_location.setText(CITY);
                getTodayWeatherInfo(CITY);
                //uploadToFirestore(CITY);
            });
        }
    }
//
//    private void uploadToFirestore(String city) {
//        final ArrayList<String> list = new ArrayList<>();
//        db = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = db.getReference("contactForm");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               for(DataSnapshot snapshot : dataSnapshot.child("latitude").getChildren()){
//
//               }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("FAIL1", "Failed to read value.", error.toException());
//            }
//        });
//    }
}
