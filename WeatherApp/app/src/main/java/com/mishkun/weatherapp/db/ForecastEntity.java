package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 08.08.2017
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Forecast")
public class ForecastEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "dt")
    private int dt;

    @ColumnInfo(name = "temp")
    private double temp;

    @ColumnInfo(name = "pressure")
    private double pressure;

    @ColumnInfo(name = "humidity")
    private int humidity;

    @ColumnInfo(name = "weather")
    private int weather;

    @ColumnInfo(name = "speed")
    private double speed;

    @ColumnInfo(name = "deg")
    private int deg;

    @ColumnInfo(name = "cloud")
    private int cloud;

    public ForecastEntity(int dt, double temp, double pressure, int humidity, int weather, double speed, int deg, int cloud) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.deg = deg;
        this.cloud = cloud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }
}
