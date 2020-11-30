package com.Fuel.fuelservice.ui.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.TimeUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class UserPositionFinder {

    private Context context;
    LatLng userPosistion = null;

    boolean check;

    FusedLocationProviderClient fusedLocationProviderClient;
    int LOCATION_REQUEST_CODE = 10001;

    public UserPositionFinder(Context context) {
        this.context = context;
    }

    public UserPositionFinder() {

    }

    public LatLng getLastLocation() {

        check = false;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        System.out.println("HEIII");

        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();


        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                check = true;

                if (location != null) {
                    userPosistion = new LatLng(location.getLatitude(), location.getLongitude());

                } else  {

                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                check = true;
            }
        });
        return userPosistion;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getLastLocation();
            } else {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity)context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions((AppCompatActivity)context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions((AppCompatActivity)context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    }
                }
            }
        }
    }
}
