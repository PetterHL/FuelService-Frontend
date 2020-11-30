package com.Fuel.fuelservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.Api.ApiClient;

import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripCalculator extends Fragment {

    EditText editTextUsername, editTextPwd;
    private User user = new User();

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tripcalculator, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsernameOnLogin);
        editTextPwd = view.findViewById(R.id.editTextTextPassword);
        return view;
    }
}
