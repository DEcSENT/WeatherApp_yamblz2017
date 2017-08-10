package com.mishkun.weatherapp.data.current_weather;

import com.mishkun.weatherapp.data.forecast.forecastEntity.ForecastWeather;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApi {

    @GET("weather")
    Observable<WeatherRaw> getWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);

    @GET("forecast/daily")
    Single<ForecastWeather> getForecastWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey, @Query("cnt") int days);
}
