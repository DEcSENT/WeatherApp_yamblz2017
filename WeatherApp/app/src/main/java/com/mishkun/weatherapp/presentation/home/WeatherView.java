package com.mishkun.weatherapp.presentation.home;

import com.mishkun.weatherapp.db.ForecastEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface WeatherView {
    Observable<Object> getRefreshCalls();
    Consumer<WeatherViewModel> getWeatherConsumer();
    Consumer<String> getErrorConsumer();
    Consumer<Boolean> getLoadingStatusConsumer();
    void setForecastList(List<ForecastEntity> forecastList);
    void showErrorMessage(String message);
}
