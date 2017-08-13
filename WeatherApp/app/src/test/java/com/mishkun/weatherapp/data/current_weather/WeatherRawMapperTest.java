package com.mishkun.weatherapp.data.current_weather;

import com.mishkun.weatherapp.domain.entities.WeatherConditions;

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

    @Test
    public void test_codeToCondition(){
        assertEquals(WeatherConditions.THUNDERSTORM, WeatherRawMapper.codeToCondition(200));
        assertEquals(WeatherConditions.DRIZZLE, WeatherRawMapper.codeToCondition(350));
        assertEquals(WeatherConditions.RAIN, WeatherRawMapper.codeToCondition(550));
        assertEquals(WeatherConditions.SNOW, WeatherRawMapper.codeToCondition(600));
        assertEquals(WeatherConditions.FOG, WeatherRawMapper.codeToCondition(750));
        assertEquals(WeatherConditions.PARTLY_CLOUDY, WeatherRawMapper.codeToCondition(802));
        assertEquals(WeatherConditions.CLOUDY, WeatherRawMapper.codeToCondition(850));

        assertEquals(WeatherConditions.CLEAR, WeatherRawMapper.codeToCondition(900));
    }
}