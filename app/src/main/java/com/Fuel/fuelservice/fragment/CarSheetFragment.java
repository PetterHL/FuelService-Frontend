package com.Fuel.fuelservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarSheetFragment extends BottomSheetDialogFragment{

    private TextView textViewregNumber, textViewManufacturerModel, textViewFuelType, textViewFuelUsage;
    private ImageView deleteCar;
    private String regNumber;
    public CarSheetFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.car_info_sheet, container, false);
        deleteCar = view.findViewById(R.id.deleteCar);
        textViewregNumber = view.findViewById(R.id.regNumber);
        textViewManufacturerModel = view.findViewById(R.id.manufacturer_model);
        textViewFuelType = view.findViewById(R.id.fuelType);
        textViewFuelUsage = view.findViewById(R.id.fuelUsage);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            regNumber = bundle.getString("regNumber");
            String manufacturer_model = bundle.getString("manufacturer_model");
            String fuelType = bundle.getString("PetrolPrice");
            String fuelUsage = bundle.getString("fuelUsage");

            textViewregNumber.setText(regNumber);
            textViewManufacturerModel.setText(manufacturer_model);
            textViewFuelType.setText(fuelType);
            textViewFuelUsage.setText(fuelUsage);

        }
        textViewFuelType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Fuel Type", Toast.LENGTH_SHORT).show();

            }
        });

        textViewFuelUsage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Fuel Consumption pr 10km", Toast.LENGTH_SHORT).show();

            }
        });
        deleteCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // deleteCar();
                dismiss();
            }
        });

        return view;
    }

        public void deleteCar() {

            Call<ResponseBody> call = ApiClient
                    .getSINGLETON(false)
                    .getApi()
                    .deleteCar(regNumber);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Deleted car", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getContext(), "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


    }

}
