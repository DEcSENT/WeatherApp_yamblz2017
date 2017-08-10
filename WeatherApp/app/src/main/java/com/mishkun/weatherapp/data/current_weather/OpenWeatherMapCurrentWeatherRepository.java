package com.mishkun.weatherapp.data.current_weather;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Database;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.mishkun.weatherapp.db.CacheEntity;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;
import com.mishkun.weatherapp.domain.outerworld.CurrentWeatherProvider;
import com.mishkun.weatherapp.presentation.home.WeatherMapper;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.mishkun.weatherapp.Constants.API_KEY_WEATHER;

public class OpenWeatherMapCurrentWeatherRepository implements CurrentWeatherProvider {
    private final OpenWeatherMapApi openWeatherMapApi;
    private DataBase dataBase;

    @Inject
    public OpenWeatherMapCurrentWeatherRepository(@NonNull OpenWeatherMapApi openWeatherMapApi, DataBase database) {
        this.openWeatherMapApi = openWeatherMapApi;
        this.dataBase = database;
    }

    @Override
    public Completable refreshData(@NonNull Location location) {
        return Completable.fromObservable(openWeatherMapApi.getWeather(location.getLatitude(), location.getLongitude(), API_KEY_WEATHER)
                .doOnNext(this::cacheIt)
                .map((weatherRaw) -> WeatherRawMapper.toDomain(weatherRaw, new Date().getTime())));
    }

    @SuppressLint("CommitPrefEdits")
    private void cacheIt(WeatherRaw weatherRaw) {
        Gson gson = new Gson();
        String weatherjson = gson.toJson(weatherRaw, WeatherRaw.class);
        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setCachedWeather(weatherjson);
        dataBase.cacheEntityDAO().insertCache(cacheEntity);
    }

    @Override
    public Flowable<Weather> getCurrentWeatherSubscription() {
        return dataBase.cacheEntityDAO().getCache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(cacheEntity -> {
                    String stringCache = cacheEntity.getCachedWeather();
                    Gson gson = new Gson();
                    WeatherRaw weatherRaw = gson.fromJson(stringCache, WeatherRaw.class);
                    return WeatherRawMapper.toDomain(weatherRaw, new Date().getTime());
                });
    }
}
