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
    private static final String TAG = OpenWeatherMapCurrentWeatherRepository.class.getSimpleName();
    private final OpenWeatherMapApi openWeatherMapApi;
    // to not to include room or other ORM now, I use this dirty hack
    //private final BehaviorRelay<Weather> weatherBehaviorSubject;
    private final Context context;
    private final String cache = "cache";

    private DataBase dataBase;

    @Inject
    public OpenWeatherMapCurrentWeatherRepository(@NonNull OpenWeatherMapApi openWeatherMapApi, @NonNull Context context, DataBase database) {
        this.openWeatherMapApi = openWeatherMapApi;
        this.context = context;
        this.dataBase = database;
        //weatherBehaviorSubject = BehaviorRelay.createDefault(getDefaultWeather());
    }

//    private Weather getDefaultWeather() {
//        WeatherRaw weatherRaw = null;
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences(cache, Context.MODE_PRIVATE);
//        String jsonFile = sharedPreferences.getString(cache, "");
//        Gson gson = new Gson();
//
////        Single<CacheEntity> single = dataBase.cacheEntityDAO().getCache()
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread());
//
//        //CacheEntity cacheEntity = single.blockingGet();
//
//        //Здесь надо возвращать обсервабле?
//
//        weatherRaw = gson.fromJson(jsonFile, WeatherRaw.class);
//
//        if (weatherRaw != null) {
//            return WeatherRawMapper.toDomain(weatherRaw, new Date().getTime());
//        }
//        return new Weather(new Temperature(273), 0, 0, WeatherConditions.CLEAR, 0, 1500046530, "MoscoW");
//    }

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
