package com.mishkun.weatherapp.data.forecast;
/*
 * Created by DV on Space 5 
 * 08.08.2017
 */

import com.mishkun.weatherapp.data.current_weather.OpenWeatherMapApi;
import com.mishkun.weatherapp.data.forecast.forecastEntity.ForecastWeather;
import com.mishkun.weatherapp.data.forecast.forecastEntity.WeatherList;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.db.ForecastEntity;
import com.mishkun.weatherapp.domain.entities.Location;

import javax.inject.Inject;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.mishkun.weatherapp.Constants.API_KEY_WEATHER;

public class OpenWeatherForecastRepository {

    /* Days count for forecast. */
    private final int DAYS_COUNT = 7;

    private DataBase dataBase;
    private OpenWeatherMapApi openWeatherMapApi;
    private Scheduler schedulerIO;

    @Inject
    public OpenWeatherForecastRepository(@NonNull DataBase dataBase, @NonNull OpenWeatherMapApi openWeatherMapApi, Scheduler scheduler) {
        this.dataBase = dataBase;
        this.openWeatherMapApi = openWeatherMapApi;
        schedulerIO = scheduler;
    }

    public void getFavouriteCityForecast(){
        Single<CityEntity> cityEntitySingle = dataBase.cityDao().getFavourite()
                .subscribeOn(schedulerIO)
                .observeOn(AndroidSchedulers.mainThread());

        cityEntitySingle.subscribe(new SingleObserver<CityEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CityEntity cityEntity) {
                Location location = new Location(Double.parseDouble(cityEntity.getLatitude()), Double.parseDouble(cityEntity.getLongitude()));
                getForecast(location);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Error!", "No favourite here! And: " +  e);
            }
        });
    }

    public void getForecast(Location location){
        Single<ForecastWeather> forecastWeatherSingle = openWeatherMapApi
                .getForecastWeather(location.getLatitude(), location.getLongitude(), API_KEY_WEATHER, DAYS_COUNT)
                .subscribeOn(schedulerIO)
                .observeOn(AndroidSchedulers.mainThread());

        forecastWeatherSingle.subscribe(new SingleObserver<ForecastWeather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ForecastWeather forecastWeather) {
                List<ForecastEntity> listForecast = new ArrayList<>();
                for(WeatherList i: forecastWeather.getWeatherList()){
                    listForecast.add(ForecastMapper.toDomain(i));
                }
                deleteAndCache(listForecast);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Error!", "No internet?", e);
            }
        });
    }

    public void deleteAndCache(List<ForecastEntity> forecastEntityList){
        Completable.fromAction(() -> dataBase.forecastEntityDAO().deleteAllForecast())
                .subscribeOn(schedulerIO)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> cacheForecast(forecastEntityList))
                .subscribe();
    }

    public void cacheForecast(List<ForecastEntity> forecastEntityList){
        Completable.fromAction(() -> dataBase.forecastEntityDAO().insertForecast(forecastEntityList))
                .subscribeOn(schedulerIO)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Flowable<List<ForecastEntity>> getCachedForecast(){
        return dataBase.forecastEntityDAO().getAllForecast()
                .subscribeOn(schedulerIO)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
