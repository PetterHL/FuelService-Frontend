package com.Fuel.fuelservice.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.DistanceSorter;
import com.Fuel.fuelservice.FuelStationRecViewAdapter;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.Maps.UserPositionFinder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationFragment extends Fragment {

    public ArrayList<FuelStations> fuelStations = new ArrayList<>();
    private FuelStationRecViewAdapter adapter;
    private RecyclerView itemRecyclerView;
    AppCompatRadioButton nearbyButton, favoriteButton, cheapButton;

    private float menuSelect = 1;

    UserPositionFinder userPositionFinder;
    private LatLng stationPosition;

    LatLng userPosistion = null;
    Context context;

    FusedLocationProviderClient fusedLocationProviderClient;
    int LOCATION_REQUEST_CODE = 10001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuelstations, container, false);
        setItemsList();
        itemRecyclerView = view.findViewById(R.id.recyclerView);
        nearbyButton = view.findViewById(R.id.nearbyButton);
        favoriteButton = view.findViewById(R.id.favoriteButton);
        cheapButton = view.findViewById(R.id.cheapButton);

        context = getContext();

        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                menuSelect = 1;
                setNearbyButton();
                setItemsList();
            }
        });
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                menuSelect = 2;
                setFavoriteButton();
                setFavoritedItemList();
            }
        });
        cheapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                menuSelect = 3;
                setCheapButton();
                setCheapestStationsItemList();
            }
        });

        adapter = new FuelStationRecViewAdapter(getContext());
        adapter.setFuelStations(fuelStations);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        return view;
    }


    /**
     * Changes colours on radio buttons when nearby button is selected.
     */
    private void setNearbyButton(){
        nearbyButton.setTextColor(Color.WHITE);
        favoriteButton.setTextColor(Color.RED);
        cheapButton.setTextColor(Color.RED);
        cheapButton.setBackgroundResource(R.drawable.radio_button_cheapest_unchecked);
        nearbyButton.setBackgroundResource(R.drawable.radio_button_nearby_checked);
        favoriteButton.setBackgroundResource(R.drawable.radio_button_favorite_unchecked);
    }
    /**
     * Changes colours on radio buttons when favorite button is selected.
     */
    private void setFavoriteButton(){
        nearbyButton.setTextColor(Color.RED);
        favoriteButton.setTextColor(Color.WHITE);
        cheapButton.setTextColor(Color.RED);
        cheapButton.setBackgroundResource(R.drawable.radio_button_cheapest_unchecked);
        nearbyButton.setBackgroundResource(R.drawable.radio_button_nearby_unchecked);
        favoriteButton.setBackgroundResource(R.drawable.radio_button_favorite_checked);
    }
    /**
     * Changes colours on radio buttons when cheapest button is selected.
     */
    private void setCheapButton(){
        nearbyButton.setTextColor(Color.RED);
        favoriteButton.setTextColor(Color.RED);
        cheapButton.setTextColor(Color.WHITE);
        cheapButton.setBackgroundResource(R.drawable.radio_button_cheapest_checked);
        nearbyButton.setBackgroundResource(R.drawable.radio_button_nearby_unchecked);
        favoriteButton.setBackgroundResource(R.drawable.radio_button_favorite_unchecked);
    }

    public void setItemsList() {

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getAllStations();

        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    assert response.body() != null;
                    getLastLocation();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {
            }
        });
    }

    /**
     * Makes a call to the backend to list out favorite stations.
     */
    public void setFavoritedItemList(){
        UserPrefs userPrefs = new UserPrefs(requireContext());
        String token = "Bearer " + userPrefs.getToken();

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getAllFavoritedStations(token);
        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    assert response.body() != null;
                    adapter.setFuelStations(fuelStations);
                } else {
                    Toast.makeText(getContext(), "Please log in to use this feature", Toast.LENGTH_SHORT).show();
                    fuelStations.clear();
                    adapter.setFuelStations(fuelStations);
                }

            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {

            }
        });

    }
    /**
     * Makes a call to the backend to list out stations ordered by which one is closest to current location.
     */
    public void setNearbyStationsItemList(){
        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getAllStations();
        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    assert response.body() != null;
                    System.out.println(response.body().toString());
                    fuelStations.sort(new DistanceSorter());
                    adapter.setFuelStations(fuelStations);
                    System.out.println(fuelStations.size());
                } else {
                    Toast.makeText(getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {

            }
        });
    }
    /**
     * Makes a call to the backend to list out stations ordered by which one is cheapest.
     */
    public void setCheapestStationsItemList(){
        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getCheapestStations();
        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    assert response.body() != null;
                    System.out.println(response.body().toString());
                    adapter.setFuelStations(fuelStations);
                    System.out.println(fuelStations.size());
                } else {
                    Toast.makeText(getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {

            }
        });
    }

    /**
     * Makes a call to the backend to update the fuel prices
     */
    public void updatePrices(){


        Call<ResponseBody>call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getPriceChange();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("price method ran successfully");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("price failure");
            }
        });
    }

    public void getLastLocation() {

        Task<Location> locationTask = null;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);



        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity)getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions((AppCompatActivity)getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions((AppCompatActivity)getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                }
            }
            Toast.makeText(getContext(), "You need to give the app access to use this feature", Toast.LENGTH_SHORT).show();
            return;
        } else {
            locationTask = fusedLocationProviderClient.getLastLocation();
        }


        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    userPosistion = new LatLng(location.getLatitude(), location.getLongitude());
                    System.out.println("My position " + userPosistion);
                    updateDistances(userPosistion);
                    System.out.println(fuelStations.get(0).getCoordinates());
                    if (menuSelect == 1) {
                        fuelStations.sort((f1,f2)->(f1.getUserDistance()) > ((f2.getUserDistance())) ? 1 :-1);
                    }
                    adapter.setFuelStations(fuelStations);
                } else  {
                    adapter.setFuelStations(fuelStations);
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "You need to give the app access to use this feature", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void updateDistances(LatLng userPosition) {
        userPositionFinder = new UserPositionFinder(getActivity());

        for(FuelStations fuelStations: fuelStations) {

            //Splits the coordinateString to an array
            String [] value = fuelStations.getCoordinates().split(",");

            //Changes the values from String to double
            double coordNorth = Double.parseDouble(value[0]);
            double coordWest = Double.parseDouble(value[1]);

            stationPosition = new LatLng(coordNorth, coordWest);

            double distance;

            distance = SphericalUtil.computeDistanceBetween(userPosition, stationPosition);
            distance = distance/1000;
            distance = round(distance, 2);

            fuelStations.setUserDistance(distance);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getLastLocation();
            } else {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity)getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions((AppCompatActivity)getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions((AppCompatActivity)getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    }
                }
            }
        }
    }

    //Rounds the double to specific decimals
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
