package com.mishkun.weatherapp.db;

import com.mishkun.weatherapp.data.forecast.forecastEntity.Temp;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 13.08.2017
 */
public class ForecastEntityTest {
    @Test
    public void testEquals_Symmetric() {
        ForecastEntity x = new ForecastEntity(10, 10,10, 10, 10, 10, 10, 100);  // equals and hashCode check name field value
        ForecastEntity y = new ForecastEntity(10, 10,10, 10, 10, 10, 10, 100);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}