package com.Fuel.fuelservice.ui.MyCar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.CarRecViewAdapter;
import com.Fuel.fuelservice.FuelStationRecViewAdapter;
import com.Fuel.fuelservice.Objects.Car;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.fragment.BottomSheetFragment;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCars extends Fragment {

    EditText editTextUsername, editTextPwd;
    private User user = new User();

    public ArrayList<Car> cars = new ArrayList<>();
    private CarRecViewAdapter adapter;
    private RecyclerView itemRecyclerView;
    private FloatingActionButton addCarButton;

    private Context context;

    SwipeRefreshLayout swipeRefreshLayout;

    public MyCars(Context context) {
        this.context = context;
    }

    public MyCars() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycars, container, false);

        itemRecyclerView = view.findViewById(R.id.recyclerView);
        addCarButton = view.findViewById(R.id.add_car_button);

        setItemsList();

        adapter = new CarRecViewAdapter(getContext());
        adapter.setCars(cars);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        getCars();

        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCar addCar = new AddCar();
                addCar.show(getChildFragmentManager(), "addCar");
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });


        return view;
    }

    public void setItemsList() {

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
                    adapter.setCars(cars);
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

            }
        });
    }

    public void refreshView() {
        setItemsList();

        adapter = new CarRecViewAdapter(getContext());
        adapter.setCars(cars);

        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        getCars();

        swipeRefreshLayout.setRefreshing(false);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}