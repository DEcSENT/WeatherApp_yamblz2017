package com.mishkun.weatherapp.presentation.home;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.mishkun.weatherapp.data.forecast.OpenWeatherForecastRepository;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.interactors.GetWeatherSubscription;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5
 * 11.08.2017
 */

public class WeatherRxPresenterTest {
//    private WeatherRxPresenter weatherRxPresenter;
//    @Mock
//    private GetWeatherSubscription getWeatherSubscription;
//    @Mock
//    private UpdateWeather updateWeather;
//    @Mock
//    private WeatherMapper weatherMapper;
//    @Mock
//    private OpenWeatherForecastRepository openWeatherForecastRepository;
//    private BehaviorRelay<Weather> weatherStatus;
//    private BehaviorRelay<Boolean> loadingStatus;
//    private PublishRelay<String> errorMessages;
//    @Before
//    public void setUp() throws Exception {
//        weatherRxPresenter = new WeatherRxPresenter(getWeatherSubscription, updateWeather, weatherMapper, openWeatherForecastRepository);
//        weatherStatus = BehaviorRelay.create();
//        loadingStatus = BehaviorRelay.createDefault(Boolean.FALSE);
//        errorMessages = PublishRelay.create();
//        when(getWeatherSubscription.run()).thenReturn(Observable.just(any(Weather.class)));
//        this.getWeatherSubscription.run().subscribe(weatherStatus);
//    }
//
//    @Test
//    public void getBackground() throws Exception {
//    }
//
//    @Test
//    public void getForecast() throws Exception {
//        weatherRxPresenter.getForecast();
//        verify(openWeatherForecastRepository).getForecast(new Location(10, 10));
//    }
}