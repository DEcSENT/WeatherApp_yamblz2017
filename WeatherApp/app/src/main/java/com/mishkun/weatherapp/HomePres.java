package com.mishkun.weatherapp;
/*
 * Created by DV on Space 5 
 * 03.08.2017
 */

interface HomePres {

    void attachView(HomeActivity view);

    void detachView();

    void openWeatherFragment();

    void openSettingsFragment();

    void openAboutFragment();

    void openSelectCityFragment();

    void openSuggestFragment();
}
