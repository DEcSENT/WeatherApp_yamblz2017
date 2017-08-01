package com.mishkun.weatherapp.di;

import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.presentation.home.HomeFragment;
import com.mishkun.weatherapp.presentation.settings.SettingsFragment;

import dagger.Subcomponent;

@Subcomponent()
@WeatherScreen
public interface WeatherScreenComponent {
    void inject(HomeActivity homeActivity);
    void inject(HomeFragment homeFragment);
}
