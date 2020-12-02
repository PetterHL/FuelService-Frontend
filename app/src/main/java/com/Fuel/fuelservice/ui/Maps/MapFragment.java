package com.Fuel.fuelservice.ui.Maps;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.MainActivity;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.fragment.BottomSheetFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    public MapFragment() {
    }

    GoogleMap map;
    boolean readyMap = false;

    GoogleMap googleMap;


    private Context context;

    public MapFragment(Context context) {
        this.context = context;
    }


    public ArrayList<FuelStations> fuelStations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        this.context = context;

        //Sends a GET request to the server and fills the array with the fuelstations
        setFuelStationsList();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nav_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        readyMap = true;
        map = googleMap;

    }

    public void Showpopup(FuelStations fuelStation) {

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show((getActivity()).getSupportFragmentManager(), bottomSheetFragment.getTag());

        String fuelStationName = fuelStation.getName();
        String petrolString = String.valueOf(fuelStation.getPetrolPrice());
        String dieselString = String.valueOf(fuelStation.getDieselPrice());

        System.out.println(fuelStationName);


        Intent intent = new Intent(getContext(), BottomSheetFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("FuelStation", fuelStationName);
        bundle.putString("DieselPrice", petrolString);
        bundle.putString("PetrolPrice", dieselString);
        intent.putExtra("myPackage", bundle);

        bottomSheetFragment.setArguments(bundle);

    }

    public void setFuelStationsList() {

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getAllStations();

        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    addMarkers();
                }
            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {
            }
        });
    }

    public void addMarkers() {

        if (readyMap) {

            LatLng Maker = null;

            //Goes though all the different fuelstations
            for (FuelStations fuelStations : fuelStations) {
                System.out.println(fuelStations.getName());

                //Splits the coordinateString to an array
                String[] value = fuelStations.getCoordinates().split(",");

                //Changes the values from String to double
                double coordNorth = Double.valueOf(value[0]);
                double coordWest = Double.valueOf(value[1]);

                //Adds a marker to the map at the correct location
                Maker = new LatLng(coordNorth, coordWest);
                map.addMarker(new MarkerOptions().position(Maker).title(fuelStations.getName()));
                map.moveCamera(CameraUpdateFactory.newLatLng(Maker));


                double distance;

                distance = SphericalUtil.computeDistanceBetween(Maker, Maker);


            }
            //If the Maker is initialized by the for loop
            if (Maker != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(Maker, 11));
            }

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    for (FuelStations fuelStations : fuelStations) {
                        if (fuelStations.getName().equals(marker.getTitle())) {
                            Showpopup(fuelStations);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
