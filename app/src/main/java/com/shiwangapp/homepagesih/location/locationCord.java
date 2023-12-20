package com.shiwangapp.homepagesih.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class locationCord {

    public static String lat = "";
    public static String lon = "";
    public static String addressLine = "";



    public static void setLongitudeLatitude(Location location){
        try {
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());
            Log.d("location_lat",  lat);
            Log.d("location_lon",  lon);

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    public static String getCityNameUsingNetwork(Context context, Location location) {
        String city = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            addressLine = addresses.get(0).getLocality();
            city = addressLine;
            //city = addresses.get(0).getAddressLine(0);

            Log.d("city", city);
        } catch (Exception e) {
            Log.d("city", "Error to find the city.");
        }
        return city;

    }


}
