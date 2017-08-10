
package com.mishkun.weatherapp.data.forecast.forecastEntity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastWeather {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("list")
    @Expose
    private List<WeatherList> weatherList = null;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<WeatherList> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

}
