package com.mishkun.weatherapp.presentation.home;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

/*
 * Created by DV on Space 5 
 * 04.08.2017
 */
public class WeatherViewModelTest {
    private WeatherViewModel weatherViewModel;
    private String test1 = "Test1";
    private String test2 = "Test2";
    private String test3 = "Tes3";
    private String test4 = "Test4";
    private String test5 = "Test5";
    private int testInt = 10;

    @Before
    public void setUp() throws Exception {
        weatherViewModel = new WeatherViewModel(test1, test2, test3, test4, testInt, test5);
    }

    @Test
    public void getHumidityText() throws Exception {
        assertEquals(test1, weatherViewModel.getHumidityText());
    }

    @Test
    public void getPressureText() throws Exception {
        assertEquals(test2, weatherViewModel.getPressureText());
    }

    @Test
    public void getDegreesText() throws Exception {
        assertEquals(test3, weatherViewModel.getDegreesText());
    }

    @Test
    public void getWindText() throws Exception {
        assertEquals(test4, weatherViewModel.getWindText());
    }

    @Test
    public void getIconResource() throws Exception {
        assertEquals(testInt, weatherViewModel.getIconResource());
    }

    @Test
    public void getCityName() throws Exception {
        assertEquals(test5, weatherViewModel.getCityName());
    }
}