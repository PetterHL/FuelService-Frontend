package com.Fuel.fuelservice.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Fuel.fuelservice.ApiClient;
import com.Fuel.fuelservice.FuelStationRecViewAdapter;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FuelStationFragment {

    private RecyclerView fuelStationRecView;
    private FuelStationRecViewAdapter adapter;
    private ArrayList<FuelStations> fuelStations = new ArrayList<>();

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fuelstations, container, false);

        fuelStationRecView = view.findViewById(R.id.stationsRecView);

        setFuelStationList();

        adapter = new FuelStationRecViewAdapter(fuelStationRecView.getContext());
        adapter.setFuelStations(fuelStations);

        fuelStationRecView.setAdapter(adapter);
        fuelStationRecView.setLayoutManager(new GridLayoutManager(fuelStationRecView.getContext(), 1));
        return view;
    }

    public void  setFuelStationList() {

        retrofit2.Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllItems();

        call.enqueue(new retrofit2.Callback<List<FuelStations>>() {
            @Override
            public void onResponse(retrofit2.Call<List<FuelStations>> call, Response<List<FuelStations>> response) {
                if (response.isSuccessful()) {
                    fuelStations = (ArrayList<FuelStations>) response.body();
                    System.out.println(response.body().toString());
                    adapter.setFuelStations(fuelStations);
                }
                else {
                    Toast.makeText(fuelStationRecView.getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();
                };
            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {

            }
        });
    }
}
