package com.mishkun.weatherapp.data.google_places.repositories;
/*
 * Created by DV on Space 5 
 * 26.07.2017
 */

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.infrastructure.WeatherApplication;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsCityRepository implements CityInfoRepository{

    private Context context;
    private String LOCATION = "LOCATION";
    private String CITY_NAME = "LOCATION";
    private String LOCATION_LATITUDE = "LOCATION_LAT";
    private String LOCATION_LONGITUDE = "LOCATION_LON";
    private DataBase dataBase;

    @Inject
    public SharedPrefsCityRepository(Context context, DataBase dataBase){
        this.context = context;
        this.dataBase = dataBase;
    }

    public Completable setCityInfo(Location location, String cityName){

        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityName(cityName);
        cityEntity.setLatitude(location.getLatitude()+"");
        cityEntity.setLongitude(location.getLongitude()+"");
        cityEntity.setFavourite("0");

        return Completable.fromAction(() -> dataBase.cityDao().insertCity(cityEntity));

//        return Completable.fromAction(() -> context.getSharedPreferences(LOCATION, MODE_PRIVATE).edit()
//        .putFloat(LOCATION_LATITUDE, (float) location.getLatitude())
//        .putFloat(LOCATION_LONGITUDE, (float) location.getLongitude())
//        .putString(CITY_NAME, cityName)
//        .apply());

        // Is it good to save double to float?
    }

    public Single<Location> getCityCoordinate(){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION, MODE_PRIVATE);
//        double lat = sharedPreferences.getFloat(LOCATION_LATITUDE, 0);
//        double lon = sharedPreferences.getFloat(LOCATION_LONGITUDE, 0);
//        Single<Location> locationSingle = Single.just(new Location(lat, lon));

        Single<Location> locationSingle = dataBase.cityDao().getFavourite()
                .map(cityEntity -> (new Location(Double.parseDouble(cityEntity.getLatitude()), Double.parseDouble(cityEntity.getLongitude()))))
                .onErrorReturn(throwable -> new Location(55.45, 37.37));

        return locationSingle;
    }

    public Single<String> getCityName(){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LOCATION, MODE_PRIVATE);
//        String cityName = sharedPreferences.getString(CITY_NAME, "empty");
//        Single<String> nameSingle = Single.just(cityName);

        Single<String> nameSingle = dataBase.cityDao().getFavourite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(cityEntity -> (cityEntity.getCityName()))
                .onErrorReturn(throwable -> "Rubber city");
        return nameSingle;
    }

    @Override
    public Flowable<List<CityEntity>> getCitiesList(){
        return  dataBase.cityDao().getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setFavourite(String cityName, String lat, String favourite) {
        Completable.fromAction(() -> dataBase.cityDao().resetFavourite())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onComplete() {
                if (favourite.equals("1")) {
                    Completable.fromAction(() -> dataBase.cityDao().setFavourite(cityName, lat, "0"))
                            .subscribeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
