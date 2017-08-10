package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CityEntity.class, CacheEntity.class, ForecastEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract CityEntityDAO cityDao();
    public abstract CacheEntityDAO cacheEntityDAO();
    public abstract ForecastEntityDAO forecastEntityDAO();
}
