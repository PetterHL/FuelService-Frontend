package com.Fuel.fuelservice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.ui.Maps.MapFragment;
import com.Fuel.fuelservice.ui.Maps.UserPositionFinder;
import com.Fuel.fuelservice.ui.MyCar.MyCars;
import com.Fuel.fuelservice.ui.TripCalculator;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.createUser.RegisterFragment;
import com.Fuel.fuelservice.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.Fuel.fuelservice.ui.home.FuelStationFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private Menu navMenu;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    FusedLocationProviderClient fusedLocationProviderClient;
    UserPositionFinder userPositionFinder;

    private static final String TAG = "MainActivity";
    int LOCATION_REQUEST_CODE = 10001;

    GoogleMap map;

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            askLocationPermission();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navMenu = navigationView.getMenu();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        updateOnStartUp();

        //  Set which fragment to run when the app opens
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                    new FuelStationFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new FuelStationFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new MapFragment()).commit();
                break;
            case R.id.nav_register:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new RegisterFragment()).commit();
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new LoginFragment()).commit();
                break;
            case R.id.nav_logOut:
                UserPrefs userPrefs = new UserPrefs(this);
                userPrefs.setToken("");
                finish();
                startActivity(getIntent());
                break;

            case R.id.nav_MyCar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new MyCars()).commit();
                break;

            case R.id.nav_TripCalculator:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contatiner,
                        new TripCalculator()).commit();

        }
        drawerLayout.closeDrawer((GravityCompat.START));

        return true;
    }


    public void updateOnStartUp() {
        UserPrefs userPrefs = new UserPrefs(getApplicationContext());

        if (userPrefs.getToken().isEmpty()) {
            navMenu.findItem(R.id.nav_home).setVisible(true);
            navMenu.findItem(R.id.nav_map).setVisible(true);
            navMenu.findItem(R.id.nav_stats).setVisible(false);
            navMenu.findItem(R.id.nav_MyCar).setVisible(false);
            navMenu.findItem(R.id.nav_TripCalculator).setVisible(false);
            navMenu.findItem(R.id.nav_login).setVisible(true);
            navMenu.findItem(R.id.nav_register).setVisible(true);
            navMenu.findItem(R.id.nav_settings).setVisible(false);
            navMenu.findItem(R.id.nav_logOut).setVisible(false);
        } else {
            navMenu.findItem(R.id.nav_home).setVisible(true);
            navMenu.findItem(R.id.nav_map).setVisible(true);
            navMenu.findItem(R.id.nav_stats).setVisible(false);
            navMenu.findItem(R.id.nav_MyCar).setVisible(true);
            navMenu.findItem(R.id.nav_TripCalculator).setVisible(true);
            navMenu.findItem(R.id.nav_login).setVisible(false);
            navMenu.findItem(R.id.nav_register).setVisible(false);
            navMenu.findItem(R.id.nav_settings).setVisible(false);
            navMenu.findItem(R.id.nav_logOut).setVisible(true);
        }
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }
    }