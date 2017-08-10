package com.mishkun.weatherapp.domain.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 05.08.2017
 */
public class TemperatureTest {
    private Temperature temperature;
    private double testValue = 250.000;
    private double delta = 0.001;

    @Before
    public void setUp() throws Exception {
        temperature = new Temperature(testValue);
    }

    @Test
    public void fromKelvin() throws Exception {
       assertEquals(temperature, Temperature.fromKelvin(testValue));
    }

    @Test
    public void getKelvinDegrees() throws Exception {
        assertEquals(testValue, temperature.getKelvinDegrees(), delta);
    }

    @Test
    public void getFahrenheitDegrees() throws Exception {
        assertEquals(testValue* 9/5 - 459.67, temperature.getFahrenheitDegrees(), delta);
    }

    @Test
    public void getCelsiusDegrees() throws Exception {
        assertEquals(testValue - 273, temperature.getCelsiusDegrees(), delta);
    }

    @Test
    public void wrongValue_getCelsiusDegrees(){
        assertNotEquals(testValue - 275, temperature.getCelsiusDegrees(), delta);
    }

}