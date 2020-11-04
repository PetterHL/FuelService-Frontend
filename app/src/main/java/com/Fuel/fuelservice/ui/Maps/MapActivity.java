package com.Fuel.fuelservice.ui.Maps;
import android.app.Activity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.Fuel.fuelservice.ApiClient;
import com.Fuel.fuelservice.MainActivity;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.fragment.BottomSheetFragment;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.fragment.app.FragmentActivity.*;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public MapActivity() {
    }

    GoogleMap map;


    private Context context;

    public MapActivity(Context context) {
        this.context = context;
    }


    public ArrayList<FuelStations> fuelStations = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = context;

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

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                for(FuelStations fuelStations: fuelStations) {
                    if (fuelStations.getName().equals(marker.getTitle())) {
                        Showpopup(fuelStations);
                    }
                }
                return false;
            }
        });
    }

    public void Showpopup(FuelStations fuelStation) {

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(((FragmentActivity)MapActivity.this).getSupportFragmentManager(), bottomSheetFragment.getTag());

        String fuelStationName = fuelStation.getName();
        String petrolString = String.valueOf(fuelStation.getPetrolPrice());
        String dieselString = String.valueOf(fuelStation.getDieselPrice());

        System.out.println(fuelStationName);


        Intent intent = new Intent(MapActivity.this, BottomSheetFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("FuelStation", fuelStationName);
        bundle.putString("DieselPrice", petrolString);
        bundle.putString("PetrolPrice", dieselString);
        intent.putExtra("myPackage", bundle);

        bottomSheetFragment.setArguments(bundle);

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
