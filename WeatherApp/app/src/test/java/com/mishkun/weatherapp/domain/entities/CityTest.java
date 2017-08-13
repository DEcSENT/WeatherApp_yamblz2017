package com.mishkun.weatherapp.domain.entities;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 13.08.2017
 */
public class CityTest {
    @Test
    public void testEquals_Symmetric() {
        City x = new City("Test", new Location(10, 10));
        City y = new City("Test", new Location(10, 10));
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}