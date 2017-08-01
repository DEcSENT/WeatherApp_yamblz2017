package com.mishkun.weatherapp.data.current_weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
 * JSON mapper object. See https://openweathermap.org/current#format for details
 */
class WeatherRaw {

    @SerializedName("wind")
    Wind wind;

    @SerializedName("weather")
    List<WeatherConditions> weather = null;

    @SerializedName("main")
    Main main;

    public WeatherRaw(){

    }

    public WeatherRaw(Wind wind, List<WeatherConditions> weather, Main main) {
        this.wind = wind;
        this.weather = weather;
        this.main = main;
    }

    static class WeatherConditions {

        @SerializedName("id")
        int weather_id;
    }

    static public class Main {

        @SerializedName("temp")
        double temperature;

        @SerializedName("pressure")
        double pressureHPa;

        @SerializedName("humidity")
        double humidity;
    }

    static public class Wind {
        @SerializedName("speed")
        double windSpeed;
    }
}
