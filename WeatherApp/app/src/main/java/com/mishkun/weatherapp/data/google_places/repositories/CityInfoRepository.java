package com.mishkun.weatherapp.data.google_places.repositories;
/*
 * Created by DV on Space 5 
 * 26.07.2017
 */

import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.domain.entities.Location;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CityInfoRepository {

    Completable setCityInfo(Location location, String cityName);

    Single<Location> getCityCoordinate();

    Single<String> getCityName();

    Flowable<List<CityEntity>> getCitiesList();

    void setFavourite(String cityName, String lan, String favourite);

    Completable deleteCity(int id);
}
