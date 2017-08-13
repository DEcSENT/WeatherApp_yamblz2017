package com.mishkun.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.di.WeatherScreenComponent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements HasComponent<WeatherScreenComponent> {

    private WeatherScreenComponent weatherScreenComponent;
    private Toolbar toolbar;

    public HomePres homePresenter;

    @Override
    protected void onResume() {
        super.onResume();
        setBackground();
    }

    public HomeActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        weatherScreenComponent = ((HasComponent<AppComponent>) getApplication()).getComponent().weatherScreenComponent();
        weatherScreenComponent.inject(this);

        homePresenter = new HomePresenter(getSupportFragmentManager());
        homePresenter.attachView(this);
        if (savedInstanceState == null) {
            homePresenter.openWeatherFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.weatherIcon:
                homePresenter.openWeatherFragment();
                return true;
            case R.id.settingsIcon:
                homePresenter.openSettingsFragment();
                return true;
            case R.id.favouriteIcon:
                homePresenter.openSelectCityFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherScreenComponent = null;
        homePresenter.detachView();
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
        if(currentHour >= 0 & currentHour < 6){
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

    /*Click About button in settings fragment.*/
    public void onClickAbout(View view) {
        homePresenter.openAboutFragment();
    }

    /*Click Add button in Favourite fragment. */
    public void onClickAddNewCity(View view) {
        homePresenter.openSuggestFragment();
    }
}
