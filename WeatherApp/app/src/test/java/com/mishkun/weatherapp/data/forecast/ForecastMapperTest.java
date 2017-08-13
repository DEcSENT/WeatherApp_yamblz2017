package com.mishkun.weatherapp.data.forecast;

import com.mishkun.weatherapp.data.forecast.forecastEntity.Temp;
import com.mishkun.weatherapp.data.forecast.forecastEntity.Weather;
import com.mishkun.weatherapp.data.forecast.forecastEntity.WeatherList;
import com.mishkun.weatherapp.db.ForecastEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 09.08.2017
 */
public class ForecastMapperTest {

    private ForecastEntity forecastEntity;
    private WeatherList forecastRawItem;
    private Temp temp = new Temp();
    private Weather weather = new Weather();
    private double delta = 0.001;

    @Before
    public void setUp() throws Exception {
        forecastEntity = new ForecastEntity(10, 20, 30, 40, 50, 60, 70, 80);
        temp.setDay(20);
        temp.setNight(100);
        weather.setId(50);
        List<Weather> list = new ArrayList<>();
        list.add(weather);

        forecastRawItem = new WeatherList();
        forecastRawItem.setDt(10);
        forecastRawItem.setTemp(temp);
        forecastRawItem.setPressure(30);
        forecastRawItem.setHumidity(40);
        forecastRawItem.setWeather(list);
        forecastRawItem.setSpeed(60);
        forecastRawItem.setDeg(70);
        forecastRawItem.setClouds(80);
    }

    @Test
    public void CheckMapper() throws Exception {
        assertEquals(forecastEntity, ForecastMapper.toDomain(forecastRawItem));
    }

    @Test
    public void CheckAllValueCorrect() throws Exception {
        assertEquals(forecastEntity.getDt(), forecastRawItem.getDt(), delta);
        assertEquals(forecastEntity.getTemp(), forecastRawItem.getTemp().getDay(), delta);
        assertEquals(forecastEntity.getPressure(), forecastRawItem.getPressure(), delta);
        assertEquals(forecastEntity.getHumidity(), forecastRawItem.getHumidity(), delta);
        assertEquals(forecastEntity.getWeather(), forecastRawItem.getWeather().get(0).getId());
        assertEquals(forecastEntity.getSpeed(), forecastRawItem.getSpeed(), delta);
        assertEquals(forecastEntity.getDeg(), forecastRawItem.getDeg(), delta);
        assertEquals(forecastEntity.getCloud(), forecastRawItem.getClouds(), delta);
    }
}