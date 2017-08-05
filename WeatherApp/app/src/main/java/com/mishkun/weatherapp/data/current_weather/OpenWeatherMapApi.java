package com.mishkun.weatherapp.data.current_weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("weather")
    Observable<WeatherRaw> getWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);
}
