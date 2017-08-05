package com.mishkun.weatherapp.data.current_weather;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/*
 * Created by DV on Space 5 
 * 29.07.2017
 */
public class WeatherRawMapperTest {

    private WeatherRaw weatherRawCURRENT;
    private long timeStamp = 100000;

    @Before
    public void setUp(){
    }

    @Test
    public void test_toDomain() throws Exception {
        ArrayList<WeatherRaw.WeatherConditions> arrayList = new ArrayList<>();
        arrayList.add(new WeatherRaw.WeatherConditions());

        weatherRawCURRENT = new WeatherRaw(new WeatherRaw.Wind(),
                arrayList, new WeatherRaw.Main());

        assertEquals(timeStamp, WeatherRawMapper.toDomain(weatherRawCURRENT, timeStamp).getTimestamp());
    }
}