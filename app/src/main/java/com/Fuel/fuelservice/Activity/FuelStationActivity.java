package com.Fuel.fuelservice.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Fuel.fuelservice.R;

public class FuelStationActivity extends AppCompatActivity {

    private TextView textViewName, textViewCoordinates, textViewDieselPrice, textViewPetrolPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelstation);

        textViewName = findViewById(R.id.station_name);
     /*   textViewCoordinates = findViewById(R.id.)
        textViewDieselPrice = findViewById(R.id.)
        textViewPetrolPrice = findViewById(R.id.)
        textViewPetrolPrice = findViewById(R.id.)*/

        textViewName.setText(getIntent().getStringExtra("Fuel station"));
    }
}
