package com.mishkun.weatherapp.data.google_places.repositories;
/*
 * Created by DV on Space 5 
 * 26.07.2017
 */

import android.util.Log;

import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.domain.entities.Location;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CitiesRepository implements CityInfoRepository{

    private DataBase dataBase;
    private Scheduler schedulersIO;

    @Inject
    public CitiesRepository(DataBase dataBase, Scheduler schedulersIO){
        this.dataBase = dataBase;
        this.schedulersIO = schedulersIO;
    }

    public Completable setCityInfo(Location location, String cityName){
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityName(cityName);
        cityEntity.setLatitude(String.valueOf(location.getLatitude()));
        cityEntity.setLongitude(String.valueOf(location.getLongitude()));
        cityEntity.setFavourite("0");
        return Completable.fromAction(() -> dataBase.cityDao().insertCity(cityEntity));
    }

    public Single<Location> getCityCoordinate(){
        // Return default city {Moscow) when no favourite city in database.
        return dataBase.cityDao().getFavourite()
                .map(cityEntity -> (new Location(Double.parseDouble(cityEntity.getLatitude()), Double.parseDouble(cityEntity.getLongitude()))))
                .onErrorReturn(throwable -> new Location(55.45, 37.37));
    }

    public Single<String> getCityName(){
        // Return default city {Moscow) when no favourite city in database.
        return dataBase.cityDao().getFavourite()
                .subscribeOn(schedulersIO)
                .observeOn(AndroidSchedulers.mainThread())
                .map(cityEntity -> (cityEntity.getCityName()))
                .onErrorReturn(throwable -> "Rubber city");
    }

    @Override
    public Flowable<List<CityEntity>> getCitiesList(){
        return  dataBase.cityDao().getAllCities()
                .subscribeOn(schedulersIO)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setFavourite(String cityName, String lat, String favourite) {
        Completable.fromAction(() -> dataBase.cityDao().resetFavourite())
                .subscribeOn(schedulersIO)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onComplete() {
                if (favourite.equals("1")) {
                    Completable.fromAction(() -> dataBase.cityDao().setFavourite(cityName, lat, "0"))
                            .subscribeOn(schedulersIO)
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                } else {
                    Completable.fromAction(() -> dataBase.cityDao().setFavourite(cityName, lat, "1"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Can't reset favourite: ", " Why? Maybe: ", e);
            }
        });
    }

    @Override
    public Completable deleteCity(int cityPosition) {
        return Completable.fromAction(() -> dataBase.cityDao().delete(cityPosition))
                .subscribeOn(schedulersIO)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
