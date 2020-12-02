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
import android.widget.TextView;
import android.widget.Toast;

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

    EditText editTextDistance, editTextFuelPrice, editTextFuelUsage;
    TextView tripCostTV;
    Button addTrip;
    private User user = new User();
    public ArrayList<FuelStations> fuelStations = new ArrayList<>();
    public ArrayList<Car> cars = new ArrayList<>();
    private Car car;
    Spinner spinner;

    boolean fueltype;

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tripcalculator, container, false);

        editTextFuelUsage = view.findViewById(R.id.editTextFuelUsage);
        editTextDistance = view.findViewById(R.id.editTextDistance);
        editTextFuelPrice = view.findViewById(R.id.editTextFuelPrice);
        tripCostTV = view.findViewById(R.id.TripCostTv);
        spinner = view.findViewById(R.id.spinner);
        addTrip = view.findViewById(R.id.button);

        setItemsList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car = (Car) parent.getSelectedItem();
                displayUserData(car);
                Car car = (Car) spinner.getSelectedItem();
                if (car.getFuelUsage() != 0) {
                    editTextFuelUsage.setText(String.valueOf(car.getFuelUsage()));
                }
                
                if(car != null && (!fuelStations.isEmpty())) {
                    if (car.isPetrol()) {
                        String test = "" + fuelStations.get(0).getPetrolPrice();
                        editTextFuelPrice.setText(test);
                    }
                    if (!car.isPetrol()) {
                        String test = "" + fuelStations.get(0).getDieselPrice();
                        editTextFuelPrice.setText(test);
                    }
                }
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

                    if(car != null) {
                        if (car.isPetrol()) {
                            String test = "" + fuelStations.get(0).getPetrolPrice();
                            editTextFuelPrice.setText(test);
                        }
                        if (!car.isPetrol()) {
                            String test = "" + fuelStations.get(0).getDieselPrice();
                            editTextFuelPrice.setText(test);
                        }
                    }

                } else {
                    Toast.makeText(getContext(), "Failed to fetch items. Try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<FuelStations>> call, Throwable t) {

            }
        });
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
                    setCheapestStationsItemList();
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

        if (myAdapter.isEmpty()) {
            Toast.makeText(getContext(), "You need to add a car to use the Trip Calculator", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTrip() {
        String distanceText = editTextDistance.getText().toString();

        String fuelText = editTextFuelPrice.getText().toString();



        Car car = (Car) spinner.getSelectedItem();
        if (car.getFuelUsage() != 0) {
            editTextFuelUsage.setText(String.valueOf(car.getFuelUsage()));
        }
        if (distanceText.isEmpty()) {
            editTextDistance.setError("Please enter a distance!");
            editTextDistance.requestFocus();
            return;
        }
        if (fuelText.isEmpty()) {
            editTextFuelPrice.setError("Please enter a fuel price!");
            editTextFuelPrice.requestFocus();
            return;
        }

        double distanceDouble = Double.parseDouble(distanceText);
        double fuelDouble = Double.parseDouble(fuelText);

        String fuelUsage = editTextFuelUsage.getText().toString();
        double fuelUsageDouble = Double.parseDouble(fuelUsage);
        

        calculateFuelUsage(fuelDouble,distanceDouble,fuelUsageDouble);



    }

    public void displayUserData(Car car) {
        System.out.println(car.getManufacturer());
    }

    public void calculateFuelUsage(double fuelUsage, double distance, double fuelPrice) {

        double calc = ((distance/10) * fuelUsage) * fuelPrice;
        String calcString = String.valueOf(calc);
        tripCostTV.setText(calcString);

    }


}
