package com.mishkun.weatherapp.domain.entities;

import android.support.annotation.NonNull;

import java.util.Date;

public class Weather {
    private final Temperature temperature;
    private final double humidity;
    private final double pressureMmHg;
    private final WeatherConditions weatherConditions;
    private final double windSpeed;
    private final long timestamp;
    private String cityName;

    public Weather(@NonNull Temperature temperature, double humidity, double pressureMmHg,
                   @NonNull WeatherConditions weatherConditions, double windSpeed, long timestamp, String cityName) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressureMmHg = pressureMmHg;
        this.weatherConditions = weatherConditions;
        this.windSpeed = windSpeed;
        this.timestamp = timestamp;
        this.cityName = cityName;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressureMmHg() {
        return pressureMmHg;
    }

    public WeatherConditions getWeatherConditions() {
        return weatherConditions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Date getTime() {
        return new Date(timestamp);
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCityName(){
        return cityName;
    }
}
