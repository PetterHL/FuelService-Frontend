package com.Fuel.fuelservice.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.Fuel.fuelservice.Objects.Car;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.Api.ApiClient;

import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.SetDoubleNum;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.google.android.gms.maps.GoogleMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Fuel.fuelservice.SetDoubleNum.setDoubleNum;

public class TripCalculator extends Fragment {

    EditText editTextDistance, editTextFuelPrice;
    Button addTrip;
    private User user = new User();
    public ArrayList<Car> cars = new ArrayList<>();
    Spinner spinner;

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tripcalculator, container, false);

        editTextDistance = view.findViewById(R.id.editTextDistance);
        editTextFuelPrice = view.findViewById(R.id.editTextFuelPrice);
        spinner = view.findViewById(R.id.spinner);
        addTrip = view.findViewById(R.id.button);

        setItemsList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Car car = (Car) parent.getSelectedItem();
                displayUserData(car);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTrip.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                addTrip();
            }
        });

        return view;
    }

    public void setItemsList () {

        UserPrefs userPrefs = new UserPrefs(getContext());

        String token = "Bearer " + userPrefs.getToken();

        Call<List<Car>> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .getOwnerCars(token);

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()) {
                    cars = (ArrayList<Car>) response.body();
                    setSpinnerAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

            }
        });
    }


    public void setSpinnerAdapter() {
        ArrayAdapter<Car> myAdapter =
                new ArrayAdapter<Car>(getContext(),android.R.layout.simple_spinner_item,cars);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }

    public void addTrip() {
        //Car car = (Car) spinner.getSelectedItem();
        //if (car.getFuelUsage() != 0) {
      //      editTextFuelPrice.setText(String.valueOf(car.getFuelUsage()));
        //}
        String distanceText = editTextDistance.getText().toString();
        double distanceDouble = Double.parseDouble(distanceText);

        String fuelText = editTextFuelPrice.getText().toString();
        double fuelDouble = Double.parseDouble(fuelText);

        System.out.println(calculateFuelUsage(fuelDouble,distanceDouble));



    }

    public void displayUserData(Car car) {
        System.out.println(car.getManufacturer());
    }

    public double calculateFuelUsage(double fuelUsage, double distance) {

        return (distance/10) * fuelUsage;
    }


}