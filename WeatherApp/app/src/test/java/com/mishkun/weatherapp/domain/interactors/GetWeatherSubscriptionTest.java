package com.mishkun.weatherapp.domain.interactors;

import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;
import com.mishkun.weatherapp.domain.outerworld.CurrentWeatherProvider;
import com.mishkun.weatherapp.data.google_places.repositories.CityInfoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 30.07.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class GetWeatherSubscriptionTest {
    private GetWeatherSubscription getWeatherSubscription;
    @Mock
    private CurrentWeatherProvider currentWeatherProvider;
    @Mock
    private CityInfoRepository cityInfoRepository;
    private TestScheduler testScheduler;
    private Weather weather;
    @Before
    public void setUp() throws Exception {
        getWeatherSubscription = new GetWeatherSubscription(testScheduler, testScheduler, currentWeatherProvider, cityInfoRepository);
        weather = new Weather(new Temperature(10.0),
                10.0,
                10.0,
                WeatherConditions.RAIN,
                10.0,
               10L, "Test");
        when(currentWeatherProvider.getCurrentWeatherSubscription()).thenReturn(Flowable.just(weather));
    }

    @Test
    public void buildUseCaseObservable() throws Exception {
        getWeatherSubscription.buildUseCaseObservable();
        verify(currentWeatherProvider).getCurrentWeatherSubscription();
    }
}