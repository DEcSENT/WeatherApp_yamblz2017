package com.mishkun.weatherapp.presentation.home;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface WeatherView {
    Observable<Object> getRefreshCalls();
    Consumer<WeatherViewModel> getWeatherConsumer();
    Consumer<String> getErrorConsumer();
    Consumer<Boolean> getLoadingStatusConsumer();
}
