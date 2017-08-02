package com.mishkun.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.di.WeatherScreenComponent;
import com.mishkun.weatherapp.presentation.AboutFragment;
import com.mishkun.weatherapp.presentation.home.HomeFragment;
import com.mishkun.weatherapp.presentation.settings.SettingsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HasComponent<WeatherScreenComponent> {

    private WeatherScreenComponent weatherScreenComponent;
    private Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();
        setBackground();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (savedInstanceState == null) {
            transaction.replace(R.id.content, new HomeFragment(), HomeFragment.TAG).commit();
        }
        weatherScreenComponent = ((HasComponent<AppComponent>) getApplication()).getComponent().weatherScreenComponent();
        weatherScreenComponent.inject(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.nav_home) {
            fm.beginTransaction().replace(R.id.content, new HomeFragment(), HomeFragment.TAG).commit();
        } else if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(R.id.content, new SettingsFragment(), SettingsFragment.TAG).commit();
        } else if (id == R.id.nav_about) {
            fm.beginTransaction().replace(R.id.content, new AboutFragment(), AboutFragment.TAG).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherScreenComponent = null;
    }

    @Override
    public WeatherScreenComponent getComponent() {
        return weatherScreenComponent;
    }
    private void setBackground() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        int currentHour = Integer.parseInt(sdf.format(date));
        Log.v("time", currentHour+"");
        if(currentHour > 0 & currentHour < 6){
            toolbar.setBackgroundResource(R.color.colorTopNight);
        } else if(currentHour >= 6 & currentHour <= 10){
            toolbar.setBackgroundResource(R.color.colorTopMorning);
        } else if(currentHour > 10 & currentHour <= 19){
            toolbar.setBackgroundResource(R.color.colorTopDay);
        } else if(currentHour > 19){
            toolbar.setBackgroundResource(R.color.colorTopEvening);
        } else {
            toolbar.setBackgroundResource(R.color.colorAccent);
        }
    }
}
