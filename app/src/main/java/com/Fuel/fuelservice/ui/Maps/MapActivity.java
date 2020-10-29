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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    public ArrayList<FuelStations> fuelStations = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sends a GET request to the server and fills the array with the fuelstations
        setFuelStationsList();
        setContentView(R.layout.map_fragment);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nav_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng Maker = null;

        //Goes though all the different fuelstations
        for(FuelStations fuelStations : fuelStations) {

            //Splits the coordinateString to an array
            String [] value = fuelStations.getCoordinates().split(",");

            //Changes the values from String to double
            double coordNorth = Double.valueOf(value[0]);
            double coordWest = Double.valueOf(value[1]);

            //Adds a marker to the map at the correct location
            Maker = new LatLng(coordNorth, coordWest);
            googleMap.addMarker(new MarkerOptions().position(Maker).title(fuelStations.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(Maker));
        }

        //If the Maker is initialized by the for loop
        if(Maker != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Maker, 11));
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
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {
            }
        });
    }
}
