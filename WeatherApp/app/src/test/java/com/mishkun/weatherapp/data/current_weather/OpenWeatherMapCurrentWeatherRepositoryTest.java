package com.mishkun.weatherapp.data.current_weather;

import com.mishkun.weatherapp.db.CacheEntity;
import com.mishkun.weatherapp.db.CacheEntityDAO;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.CityEntityDAO;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 11.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherMapCurrentWeatherRepositoryTest {

    private OpenWeatherMapCurrentWeatherRepository openWeatherMapCurrentWeatherRepository;
    @Mock
    private DataBase dataBase;
    @Mock
    private OpenWeatherMapApi openWeatherMapApi;
    private TestScheduler testScheduler;
    private WeatherRaw weatherRaw;
    @Mock
    private CacheEntityDAO cacheEntityDAO;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        openWeatherMapCurrentWeatherRepository = new OpenWeatherMapCurrentWeatherRepository(openWeatherMapApi, dataBase, testScheduler);

        List<WeatherRaw.WeatherConditions> list = new ArrayList<>();
        weatherRaw = new WeatherRaw(new WeatherRaw.Wind(), list, new WeatherRaw.Main());

        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setCachedWeather("test");

        when(dataBase.cacheEntityDAO()).thenReturn(cacheEntityDAO);
        when(dataBase.cacheEntityDAO().getCache()).thenReturn(Flowable.just(cacheEntity));
    }

    @Test
    public void cacheIt() throws Exception {
        List<WeatherRaw.WeatherConditions> list = new ArrayList<>();
        weatherRaw = new WeatherRaw(new WeatherRaw.Wind(), list, new WeatherRaw.Main());
        openWeatherMapCurrentWeatherRepository.cacheIt(weatherRaw);
        verify(cacheEntityDAO).insertCache(any(CacheEntity.class));
    }

    @Test
    public void getCurrentWeatherSubscription() throws Exception {
        openWeatherMapCurrentWeatherRepository.getCurrentWeatherSubscription();
        verify(cacheEntityDAO).getCache();
    }
}