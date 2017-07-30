package com.mishkun.weatherapp.presentation.home;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by Mishkun on 21.07.2017.
 */

class WeatherViewModel {
    private final String humidityText;
    private final String pressureText;
    private final String degreesText;
    private final String windText;
    private final int iconResource;

    WeatherViewModel(@NonNull String humidityText, @NonNull String pressureText, @NonNull String degreesText, @NonNull String windText,
                     @DrawableRes int iconResource) {
        this.humidityText = humidityText;
        this.pressureText = pressureText;
        this.degreesText = degreesText;
        this.windText = windText;
        this.iconResource = iconResource;
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
}