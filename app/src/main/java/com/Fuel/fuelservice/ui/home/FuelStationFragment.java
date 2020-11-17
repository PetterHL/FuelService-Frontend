package com.Fuel.fuelservice.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.Fuel.fuelservice.preference.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationFragment extends Fragment {

    public ArrayList<FuelStations> fuelStations = new ArrayList<>();
    private FuelStationRecViewAdapter adapter;
    private RecyclerView itemRecyclerView;
    private RadioGroup group;
    AppCompatRadioButton nearbyButton, favoriteButton ,cheapButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuelstations, container, false);



        itemRecyclerView = view.findViewById(R.id.recyclerView);
        nearbyButton = view.findViewById(R.id.nearbyButton);
        favoriteButton = view.findViewById(R.id.favoriteButton);
        cheapButton = view.findViewById(R.id.cheapButton);
        group= (RadioGroup) getView().findViewById(R.id.radioGroup);

        adapter = new FuelStationRecViewAdapter(getContext());



        getFuelStations();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.nearbyButton:

                        nearbyButton.setTextColor(Color.WHITE);
                        favoriteButton.setTextColor(Color.RED);
                        cheapButton.setTextColor(Color.RED);
                        setItemsList();
                        adapter.setFuelStations(fuelStations);
                        itemRecyclerView.setAdapter(adapter);
                        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));



                    case R.id.favoriteButton:

                        nearbyButton.setTextColor(Color.RED);
                        favoriteButton.setTextColor(Color.WHITE);
                        cheapButton.setTextColor(Color.RED);
                        setItemsList();
                        adapter.setFuelStations(fuelStations);
                        itemRecyclerView.setAdapter(adapter);
                        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));


                    case R.id.cheapButton:

                        nearbyButton.setTextColor(Color.RED);
                        favoriteButton.setTextColor(Color.RED);
                        cheapButton.setTextColor(Color.WHITE);
                        setItemsList();
                        adapter.setFuelStations(fuelStations);
                        itemRecyclerView.setAdapter(adapter);
                        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));


                }
            }
        });
        return view;



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
    public void getAllFavoritedStations(){

        UserPrefs userPrefs;
        userPrefs = new UserPrefs(getContext());
        String token = "Bearer " + userPrefs.getToken();
        Call<List<FuelStations>> call = ApiClient
                .getSINGLETON()
                .getApi()
                .getAllFavoritedStations(token);
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
