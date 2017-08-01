package com.mishkun.weatherapp.data.current_weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;
import com.mishkun.weatherapp.domain.outerworld.CurrentWeatherProvider;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class OpenWeatherMapCurrentWeatherRepository implements CurrentWeatherProvider {
    private static final String TAG = OpenWeatherMapCurrentWeatherRepository.class.getSimpleName();
    private final String API_KEY = "a94b47f30f78afba43ac68effc69a24a";
    private final OpenWeatherMapApi openWeatherMapApi;
    // to not to include room or other ORM now, I use this dirty hack
    private final BehaviorRelay<Weather> weatherBehaviorSubject;
    private final Context context;
    private final String cache = "cache";

    public OpenWeatherMapCurrentWeatherRepository(@NonNull OpenWeatherMapApi openWeatherMapApi, @NonNull Context context) {
        this.openWeatherMapApi = openWeatherMapApi;
        this.context = context;
        weatherBehaviorSubject = BehaviorRelay.createDefault(getDefaultWeather());
    }

    private Weather getDefaultWeather() {
        com.mishkun.weatherapp.data.current_weather.Weather weather = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences(cache, Context.MODE_PRIVATE);

        String jsonFile = sharedPreferences.getString(cache, "");
        Gson gson = new Gson();
        weather = gson.fromJson(jsonFile, com.mishkun.weatherapp.data.current_weather.Weather.class);

        if (weather != null) {
            return WeatherMapper.toDomain(weather, new Date().getTime());
        }
        return new Weather(new Temperature(295), 90, 769, WeatherConditions.CLEAR, 0, 1500046530, "MoscoW");
    }

    @Override
    public Completable refreshData(@NonNull Location location) {
        return Completable.fromObservable(openWeatherMapApi.getWeather(location.getLatitude(), location.getLongitude(), API_KEY)
                .doOnNext(this::cacheIt)
                .map((weather) -> WeatherMapper.toDomain(weather, new Date().getTime()))
                .doOnNext(weatherBehaviorSubject));
    }

    @SuppressLint("CommitPrefEdits")
    private void cacheIt(com.mishkun.weatherapp.data.current_weather.Weather weather) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(cache, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String weatherjson = gson.toJson(weather, com.mishkun.weatherapp.data.current_weather.Weather.class);

        sharedPreferences.edit().putString(cache, weatherjson).apply();
    }

    @Override
    public Observable<Weather> getCurrentWeatherSubscription() {
        return weatherBehaviorSubject;
    }
}
