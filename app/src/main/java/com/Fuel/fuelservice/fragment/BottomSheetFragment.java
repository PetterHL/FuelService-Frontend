package com.Fuel.fuelservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.zip.Inflater;

public class BottomSheetFragment extends BottomSheetDialogFragment{

    private TextView textViewName, textViewPetrolPrice, textViewDieselPrice;
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



            System.out.println("tvStationName" + name);

            textViewName.setText(name);
            textViewPetrolPrice.setText(dieselPrice);
            textViewDieselPrice.setText(petrolPrice);
        }
        return view;


    }

}
