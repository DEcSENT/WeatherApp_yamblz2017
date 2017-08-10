package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 07.08.2017
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Cache", indices = @Index(value = "cache", unique = true))
public class CacheEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "cache")
    private String cachedWeather;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCachedWeather() {
        return cachedWeather;
    }

    public void setCachedWeather(String cachedWeather) {
        this.cachedWeather = cachedWeather;
    }
}
