package com.mishkun.weatherapp.domain.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by DV on Space 5 
 * 13.08.2017
 */

public class LocationTest {
    @Test
    public void testEquals_Symmetric() throws Exception {
        Location x = new Location(10, 10);
        Location y = new Location(10, 10);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }

}