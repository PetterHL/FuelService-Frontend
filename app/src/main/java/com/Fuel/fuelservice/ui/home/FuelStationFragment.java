package com.Fuel.fuelservice.ui.home;

import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.ApiClient;
import com.Fuel.fuelservice.FuelStationView.FuelStationRecViewAdapter;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationFragment {

    private RecyclerView fuelStationRecView;
    private FuelStationRecViewAdapter adapter;
    private ArrayList<FuelStations> fuelStations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fuelstations, container, false);

        fuelStationRecView = view.findViewById(R.id.stationsRecView);


        adapter.setFuelStations(fuelStations);

        fuelStationRecView.setAdapter(adapter);
        fuelStationRecView.setLayoutManager(new GridLayoutManager(getContext(),1));
        return view;
    }

    public void  setItemList() {

        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllItems();

        call.enqueue(new Callback<List<FuelStations>>() {
            @Override
            public void onResponse(Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    System.out.println(response.body().toString());
                    adapter.setItems(fuelStations);
                }
                else {
                    Toast.makeText(getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();
                };
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}
