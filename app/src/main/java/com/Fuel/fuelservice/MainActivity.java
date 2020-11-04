package com.Fuel.fuelservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Fuel.fuelservice.ui.Maps.MapActivity;
import com.google.android.gms.maps.GoogleMap;

import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.createUser.RegisterFragment;
import com.Fuel.fuelservice.ui.login.LoginFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.Fuel.fuelservice.ui.home.FuelStationFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private Menu navMenu;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    GoogleMap map;

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
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);

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
            navMenu.findItem(R.id.nav_fuelCalculator).setVisible(false);
            navMenu.findItem(R.id.nav_login).setVisible(true);
            navMenu.findItem(R.id.nav_register).setVisible(true);
            navMenu.findItem(R.id.nav_settings).setVisible(true);
            navMenu.findItem(R.id.nav_logOut).setVisible(false);
        } else {
            navMenu.findItem(R.id.nav_home).setVisible(true);
            navMenu.findItem(R.id.nav_map).setVisible(true);
            navMenu.findItem(R.id.nav_stats).setVisible(true);
            navMenu.findItem(R.id.nav_MyCar).setVisible(true);
            navMenu.findItem(R.id.nav_fuelCalculator).setVisible(true);
            navMenu.findItem(R.id.nav_login).setVisible(false);
            navMenu.findItem(R.id.nav_register).setVisible(false);
            navMenu.findItem(R.id.nav_settings).setVisible(true);
            navMenu.findItem(R.id.nav_logOut).setVisible(true);
        }
    }
}