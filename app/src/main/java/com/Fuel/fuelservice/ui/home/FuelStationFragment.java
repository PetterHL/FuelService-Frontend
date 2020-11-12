package com.Fuel.fuelservice.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.FuelStationRecViewAdapter;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationFragment extends Fragment {

    public ArrayList<FuelStations> fuelStations = new ArrayList<>();
    private FuelStationRecViewAdapter adapter;
    private RecyclerView itemRecyclerView;
    AppCompatRadioButton nearbyButton, favoriteButton ,cheapButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuelstations, container, false);

        itemRecyclerView = view.findViewById(R.id.recyclerView);
        nearbyButton = view.findViewById(R.id.nearbyButton);
        favoriteButton = view.findViewById(R.id.favoriteButton);
        cheapButton = view.findViewById(R.id.cheapButton);

        setItemsList();

        adapter = new FuelStationRecViewAdapter(getContext());
        adapter.setFuelStations(fuelStations);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        getFuelStations();
        return view;



    }

    public void onRadioButtonClick (View view){
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()) {
            case R.id.nearbyButton:
                if (isSelected) {
                    nearbyButton.setTextColor(Color.WHITE);
                    favoriteButton.setTextColor(Color.RED);
                    cheapButton.setTextColor(Color.RED);
                    setItemsList();
                }
            case R.id.favoriteButton:
                if (isSelected) {
                    nearbyButton.setTextColor(Color.RED);
                    favoriteButton.setTextColor(Color.WHITE);
                    cheapButton.setTextColor(Color.RED);
                    setItemsList();
                }
            case R.id.cheapButton:
                if (isSelected) {
                    nearbyButton.setTextColor(Color.RED);
                    favoriteButton.setTextColor(Color.RED);
                    cheapButton.setTextColor(Color.WHITE);
                    setItemsList();
                }
        }
    }

    public void setItemsList() {

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllStations();

        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
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
    public void setFavoritedItemList(){

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllFavoritedStaions();
        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
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

    public void setNearbyStationsItemList(){
        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllStations();
        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
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

    public ArrayList<FuelStations> getFuelStations() {
        return fuelStations;
    }
}
