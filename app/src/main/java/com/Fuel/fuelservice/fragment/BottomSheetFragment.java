package com.Fuel.fuelservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private TextView textViewName, textViewPetrolPrice, textViewDieselPrice;
    private String bundleName;
    private User user;


    public BottomSheetFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fuel_station_sheet_information, container, false);

        textViewName = view.findViewById(R.id.tvStationName);
        textViewPetrolPrice = view.findViewById(R.id.tvPetrolPrice);
        textViewDieselPrice = view.findViewById(R.id.tvDieselPrice);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("FuelStation");
            String dieselPrice = bundle.getString("DieselPrice");
            String petrolPrice = bundle.getString("PetrolPrice");
            bundleName = name;
            System.out.println("tvStationName" + bundleName);
            textViewName.setText(bundleName);
            textViewPetrolPrice.setText(dieselPrice);
            textViewDieselPrice.setText(petrolPrice);
        }

        CheckBox chk = (CheckBox) view.findViewById(R.id.favorite);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {

                    addFavorite();

                    //System.out.println(user);
                } else {
                    Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void addFavorite() {

        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
        Bundle bundle = this.getArguments();
        assert bundle != null;
        String FuelStationId = bundle.getString("FuelStationId");
        System.out.println(FuelStationId);
        UserPrefs userPrefs = new UserPrefs(requireContext());
        String token = "Bearer " + userPrefs.getToken();

        // Adding favorite using api call
        Call<ResponseBody> call = ApiClient
                .getSINGLETON()
                .getApi()
                .setFavorite(token, FuelStationId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        response.body().string();
                        System.out.println(response);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }


        });
    }
}