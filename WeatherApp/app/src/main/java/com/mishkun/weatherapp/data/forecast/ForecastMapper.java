package com.mishkun.weatherapp.data.forecast;
/*
 * Created by DV on Space 5 
 * 08.08.2017
 */

import com.mishkun.weatherapp.data.forecast.forecastEntity.WeatherList;
import com.mishkun.weatherapp.db.ForecastEntity;

public class ForecastMapper {
    static ForecastEntity toDomain(WeatherList forecastRawItem){
        return new ForecastEntity(forecastRawItem.getDt(),
                forecastRawItem.getTemp().getDay(),
                forecastRawItem.getPressure(),
                forecastRawItem.getHumidity(),
                        forecastRawItem.getWeather().get(0).getId(),
                        forecastRawItem.getSpeed(), forecastRawItem.getDeg(), forecastRawItem.getClouds());
    }
}
