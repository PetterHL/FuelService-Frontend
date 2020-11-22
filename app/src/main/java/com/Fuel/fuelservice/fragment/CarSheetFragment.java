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

public class CarSheetFragment extends BottomSheetDialogFragment{

    private TextView textViewregNumber, textViewManufacturerModel, textViewFuelType;
    public CarSheetFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.car_info_sheet, container, false);

        textViewregNumber = view.findViewById(R.id.regNumber);
        textViewManufacturerModel = view.findViewById(R.id.manufacturer_model);
        textViewFuelType = view.findViewById(R.id.fuelType);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String regNumber = bundle.getString("regNumber");
            String manufacturer_model = bundle.getString("manufacturer_model");
            String fuelType = bundle.getString("PetrolPrice");

            textViewregNumber.setText(regNumber);
            textViewManufacturerModel.setText(manufacturer_model);
            textViewFuelType.setText(fuelType);
        }
        return view;


    }

}
