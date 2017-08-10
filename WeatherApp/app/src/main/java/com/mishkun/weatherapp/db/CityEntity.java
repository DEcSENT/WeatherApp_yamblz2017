package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "CityEntity", indices = @Index(value = "latitude", unique = true))
public class CityEntity {

    public CityEntity() {
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "city_name")
    private String cityName;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "favourite")
    private String favourite;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        if (uid != that.uid) return false;
        if (!cityName.equals(that.cityName)) return false;
        if (!latitude.equals(that.latitude)) return false;
        if (!longitude.equals(that.longitude)) return false;
        return favourite.equals(that.favourite);
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + cityName.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + favourite.hashCode();
        return result;
    }
}
