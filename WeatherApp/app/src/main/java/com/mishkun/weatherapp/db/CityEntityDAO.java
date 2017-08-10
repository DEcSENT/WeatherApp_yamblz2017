package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CityEntityDAO {

    @Query("SELECT * FROM CityEntity")
    Flowable<List<CityEntity>> getAllCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(CityEntity cityEntity);

    @Query("DELETE FROM CityEntity WHERE uid = :id")
    void delete(int id);

    @Query("UPDATE CityEntity SET favourite = :fav WHERE city_name LIKE :cityName AND latitude LIKE :lat")
    void setFavourite(String cityName, String lat, String fav);

    @Query("SELECT * FROM CityEntity WHERE favourite = 1")
    Single<CityEntity> getFavourite();

    @Query("UPDATE CityEntity SET favourite = 0 WHERE favourite LIKE 1")
    void resetFavourite();
}
