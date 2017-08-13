package com.mishkun.weatherapp.presentation.home;

import android.util.Log;
import android.view.View;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.data.forecast.OpenWeatherForecastRepository;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.ForecastEntity;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;
import com.mishkun.weatherapp.domain.interactors.GetWeatherSubscription;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5
 * 11.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherRxPresenterTest {
    private WeatherRxPresenter weatherRxPresenter;
    @Mock
    private GetWeatherSubscription getWeatherSubscription;
    @Mock
    private UpdateWeather updateWeather;
    @Mock
    private WeatherMapper weatherMapper;
    @Mock
    private WeatherView view;
    @Mock
    private OpenWeatherForecastRepository openWeatherForecastRepository;
    private ForecastEntity forecastEntity = new ForecastEntity(10, 10, 10, 10, 10, 10, 10, 10);
    private List<ForecastEntity> entityList;
    @Before
    public void setUp() throws Exception {
        Weather weather = new Weather(new Temperature(10), 10,
                10, WeatherConditions.RAIN, 10, 10L, "test");
        when(getWeatherSubscription.run()).thenReturn(Observable.just(weather));
        weatherRxPresenter = new WeatherRxPresenter(getWeatherSubscription, updateWeather, weatherMapper, openWeatherForecastRepository);

        when(view.getRefreshCalls()).thenReturn(Observable.never());
        when(updateWeather.run()).thenReturn(Completable.complete());

        WeatherViewModel weatherView = new WeatherViewModel("10", "10", "10", "10", R.drawable.sun, "test");
        when(view.getWeatherConsumer()).thenReturn(new io.reactivex.functions.Consumer<WeatherViewModel>() {
            @Override
            public void accept(WeatherViewModel weatherViewModel) throws Exception {
                weatherViewModel = weatherView;
            }
        });
        WeatherViewModel weatherViewModel = new WeatherViewModel("tes", "dfs", "dfs", "fdsf", R.drawable.sun, "dsf");
        when(weatherMapper.toWeatherViewModel(weather)).thenReturn(weatherViewModel);
        when(view.getLoadingStatusConsumer()).thenReturn(aBoolean -> aBoolean = true);
        when(view.getErrorConsumer()).thenReturn(s -> s = "test");

        weatherRxPresenter.attachView(view);

        entityList = new ArrayList<>();
        entityList.add(forecastEntity);
        when(openWeatherForecastRepository.getCachedForecast()).thenReturn(Flowable.just(entityList).onErrorReturnItem(entityList));
    }

    @Test
    public void getBackground() throws Exception {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        int currentHour = Integer.parseInt(sdf.format(date));
        Log.v("time", currentHour + "");
        if (currentHour >= 0 & currentHour < 6) {
            assertEquals(R.drawable.gradient_night, weatherRxPresenter.getBackground());
        } else if (currentHour >= 6 & currentHour <= 10) {
            assertEquals(R.drawable.gradient_morning, weatherRxPresenter.getBackground());
        } else if (currentHour > 10 & currentHour <= 19) {
            assertEquals(R.drawable.gradient_day, weatherRxPresenter.getBackground());
        } else if (currentHour > 19) {
            assertEquals(R.drawable.gradient_evening, weatherRxPresenter.getBackground());
        }
    }

    @Test
    public void getForecast() throws Exception {
        weatherRxPresenter.getForecast();
        verify(openWeatherForecastRepository).getCachedForecast();
    }
}