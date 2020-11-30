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
import com.Fuel.fuelservice.preference.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
        setItemsList();
        itemRecyclerView = view.findViewById(R.id.recyclerView);
        nearbyButton = view.findViewById(R.id.nearbyButton);
        favoriteButton = view.findViewById(R.id.favoriteButton);
        cheapButton = view.findViewById(R.id.cheapButton);

        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                setNearbyButton();
                setItemsList();
            }
        });
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                setFavoriteButton();
                setFavoritedItemList();
            }
        });
        cheapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrices();
                setCheapButton();
                setCheapestStationsItemList();
            }
        });

        adapter = new FuelStationRecViewAdapter(getContext());
        adapter.setFuelStations(fuelStations);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));


        return view;

    }


    private void setNearbyButton(){
        nearbyButton.setTextColor(Color.WHITE);
        favoriteButton.setTextColor(Color.RED);
        cheapButton.setTextColor(Color.RED);
        cheapButton.setBackgroundResource(R.drawable.radio_button_cheapest_unchecked);
        nearbyButton.setBackgroundResource(R.drawable.radio_button_nearby_checked);
        favoriteButton.setBackgroundResource(R.drawable.radio_button_favorite_unchecked);
    }
    private void setFavoriteButton(){
        nearbyButton.setTextColor(Color.RED);
        favoriteButton.setTextColor(Color.WHITE);
        cheapButton.setTextColor(Color.RED);
        cheapButton.setBackgroundResource(R.drawable.radio_button_cheapest_unchecked);
        nearbyButton.setBackgroundResource(R.drawable.radio_button_nearby_unchecked);
        favoriteButton.setBackgroundResource(R.drawable.radio_button_favorite_checked);
    }
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
                    System.out.println(response.body().toString());
                    adapter.setFuelStations(fuelStations);
                    System.out.println(fuelStations.size());
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

}
