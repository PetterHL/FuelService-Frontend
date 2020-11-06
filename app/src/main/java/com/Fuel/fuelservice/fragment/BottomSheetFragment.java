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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.List;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private TextView textViewName, textViewPetrolPrice, textViewDieselPrice;
    Bundle bundle = this.getArguments();

    public BottomSheetFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fuel_station_sheet_information, container, false);

        textViewName = view.findViewById(R.id.tvStationName);
        textViewPetrolPrice = view.findViewById(R.id.tvPetrolPrice);
        textViewDieselPrice = view.findViewById(R.id.tvDieselPrice);



        CheckBox chk = (CheckBox) view.findViewById(R.id.favorite);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        /*    User user = new User();
                        String stationName = bundle.getString("FuelStation");
                        user.addFavoriteStation(stationName);
                        System.out.println(user);*/
                }
                else{
                    Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
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




        /*public void setCurrentUser() {
            Call<ResponseBody> call = ApiClient
                    .getSINGLETON()
                    .getApi()
                    .getCurrentUser();
        }*/




