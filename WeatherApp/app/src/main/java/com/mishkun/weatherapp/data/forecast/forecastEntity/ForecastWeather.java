
package com.mishkun.weatherapp.data.forecast.forecastEntity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastWeather {

    @SerializedName("city")
    @Expose
    private City city;
//    @SerializedName("cod")
//    @Expose
//    private String cod;
//    @SerializedName("message")
//    @Expose
//    private double message;
//    @SerializedName("cnt")
//    @Expose
//    private int cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherList> weatherList = null;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
//
//    public String getCod() {
//        return cod;
//    }
//
//    public void setCod(String cod) {
//        this.cod = cod;
//    }
//
//    public double getMessage() {
//        return message;
//    }
//
//    public void setMessage(double message) {
//        this.message = message;
//    }
//
//    public int getCnt() {
//        return cnt;
//    }
//
//    public void setCnt(int cnt) {
//        this.cnt = cnt;
//    }

    public java.util.List<WeatherList> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(java.util.List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

}
