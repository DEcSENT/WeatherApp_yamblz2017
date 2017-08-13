package com.mishkun.weatherapp.data.forecast;

import com.mishkun.weatherapp.data.current_weather.OpenWeatherMapApi;
import com.mishkun.weatherapp.data.forecast.forecastEntity.City;
import com.mishkun.weatherapp.data.forecast.forecastEntity.ForecastWeather;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.CityEntityDAO;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.db.ForecastEntity;
import com.mishkun.weatherapp.db.ForecastEntityDAO;
import com.mishkun.weatherapp.domain.entities.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 10.08.2017
 */

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherForecastRepositoryTest {

    private OpenWeatherForecastRepository openWeatherForecastRepository;

    @Mock
    private DataBase dataBase;
    @Mock
    private CityEntityDAO cityEntityDAO;
    @Mock
    private ForecastEntityDAO forecastEntityDAO;
    @Mock
    private OpenWeatherMapApi openWeatherMapApi;

    private CityEntity cityEntity;
    private ForecastWeather forecastWeather;
    private City city;
    private ForecastEntity forecastEntity;
    private List<ForecastEntity> forecastEntityList = new ArrayList<>();

    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        openWeatherForecastRepository = new OpenWeatherForecastRepository(dataBase, openWeatherMapApi, testScheduler);

        cityEntity = new CityEntity();
        cityEntity.setCityName("TestCityName");
        cityEntity.setFavourite("1");
        cityEntity.setLongitude("50");
        cityEntity.setLatitude("50");
        cityEntity.setUid(100);

        forecastWeather = new ForecastWeather();
        city = new City();
        city.setName("test");
        city.setId(10);
        forecastWeather.setCity(city);

        when(dataBase.cityDao()).thenReturn(cityEntityDAO);
        when(dataBase.cityDao().getFavourite()).thenReturn(Single.just(cityEntity));

        forecastEntity = new ForecastEntity(10, 20, 30, 40, 50, 60, 70, 80);
        forecastEntityList.add(forecastEntity);
        when(dataBase.forecastEntityDAO()).thenReturn(forecastEntityDAO);
        when(dataBase.forecastEntityDAO().getAllForecast()).thenReturn(Flowable.just(forecastEntityList));
    }

    @Test
    public void getFavouriteCityForecast() throws Exception {
        openWeatherForecastRepository.getFavouriteCityForecast();
        verify(dataBase.cityDao()).getFavourite();
    }

    @Test
    public void deleteAndCache() throws Exception {
        openWeatherForecastRepository.deleteAndCache(forecastEntityList);
        testScheduler.triggerActions();
        verify(forecastEntityDAO).deleteAllForecast();
    }

    @Test
    public void cacheForecast() throws Exception {
        openWeatherForecastRepository.cacheForecast(forecastEntityList);
        testScheduler.triggerActions();
        verify(forecastEntityDAO).insertForecast(forecastEntityList);
    }

    @Test
    public void getCachedForecast() throws Exception {
        openWeatherForecastRepository.getCachedForecast();
        verify(dataBase.forecastEntityDAO()).getAllForecast();
    }
}