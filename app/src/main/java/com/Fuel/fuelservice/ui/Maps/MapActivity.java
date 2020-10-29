package com.Fuel.fuelservice.ui.Maps;
import android.app.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.Fuel.fuelservice.ApiClient;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    public ArrayList<FuelStations> fuelStations = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFuelStationsList();
        setContentView(R.layout.map_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nav_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for(FuelStations fuelStations : fuelStations) {

            String [] value = fuelStations.getCoordinates().split(",");

            double coordNorth = Double.valueOf(value[0]);
            double coordWest = Double.valueOf(value[1]);

            LatLng heihei = new LatLng(coordNorth, coordWest);
            googleMap.addMarker(new MarkerOptions().position(heihei).title(fuelStations.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(heihei));
        }
    }

    public void setFuelStationsList() {

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllStations();

        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    System.out.println("1111111111111111111111111111");
                } else {
                    System.out.println("2222222222222222222222222222");
                }

            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {
            }
        });
    }
}
