package com.mishkun.weatherapp.presentation.settings;

import android.content.Context;
import android.content.res.Resources;

import com.mishkun.weatherapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 11.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsMapperTest {
    SettingsMapper settingsMapper;
    @Mock
    public Context context;
    @Mock
    public Resources resources;

    private int[] testArray = {15, 30, 60, 180};

    private int[] array;

    @Before
    public void setUp() throws Exception {
        when(context.getResources()).thenReturn(resources);
        when(resources.getIntArray(R.array.time_schedule_preference_entry_values)).thenReturn(testArray);
        settingsMapper = new SettingsMapper(context);
    }

    @Test
    public void toViewSpinnerPosition() throws Exception {
        assertEquals(1, settingsMapper.toViewSpinnerPosition(30));
    }

    @Test
    public void wrongParam_toViewSpinnerPosition() throws Exception {
        assertNotEquals(1, settingsMapper.toViewSpinnerPosition(230));
    }

    @Test
    public void toActualMinutes() throws Exception {
        assertEquals(15, settingsMapper.toActualMinutes(0));
        assertEquals(30, settingsMapper.toActualMinutes(1));
        assertEquals(60, settingsMapper.toActualMinutes(2));
    }

    @Test
    public void millisToMinutes() throws Exception {
        assertEquals(16, settingsMapper.millisToMinutes(1000000));
        assertEquals(166, settingsMapper.millisToMinutes(10000000));
    }

    @Test
    public void minutesToMillis() throws Exception {
        assertEquals(900000, settingsMapper.minutesToMillis(15));
        assertEquals(1800000, settingsMapper.minutesToMillis(30));
    }
}