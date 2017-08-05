package com.mishkun.weatherapp.presentation.home;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

class WeatherViewModel {
    private final String humidityText;
    private final String pressureText;
    private final String degreesText;
    private final String windText;
    private final int iconResource;
    private final String cityName;

    WeatherViewModel(@NonNull String humidityText, @NonNull String pressureText, @NonNull String degreesText, @NonNull String windText,
                     @DrawableRes int iconResource, String cityName) {
        this.humidityText = humidityText;
        this.pressureText = pressureText;
        this.degreesText = degreesText;
        this.windText = windText;
        this.iconResource = iconResource;
        this.cityName = cityName;
    }

    String getHumidityText() {
        return humidityText;
    }

    String getPressureText() {
        return pressureText;
    }

    String getDegreesText() {
        return degreesText;
    }

    String getWindText() {
        return windText;
    }

    int getIconResource() {
        return iconResource;
    }

    String getCityName(){
        return cityName;
    }
}
