package com.mishkun.weatherapp.db;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 13.08.2017
 */
public class CityEntityTest {
    @Test
    public void testEquals_Symmetric() {
        CityEntity x = new CityEntity();
        x.setCityName("Test name");
        x.setLatitude("10");
        x.setLongitude("10");
        x.setUid(1);
        x.setFavourite("1");
        CityEntity y = new CityEntity();
        y.setCityName("Test name");
        y.setLatitude("10");
        y.setLongitude("10");
        y.setUid(1);
        y.setFavourite("1");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}