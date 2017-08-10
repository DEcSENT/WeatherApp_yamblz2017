package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 08.08.2017
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ForecastEntityDAO {

    @Query("SELECT * FROM Forecast")
    Flowable<List<ForecastEntity>> getAllForecast();

    @Insert
    void insertForecast(List<ForecastEntity> forecastEntity);

    @Query("DELETE FROM Forecast")
    void deleteAllForecast();
}
