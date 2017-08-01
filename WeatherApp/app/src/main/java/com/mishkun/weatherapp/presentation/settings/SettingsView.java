package com.mishkun.weatherapp.presentation.settings;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

interface SettingsView {
    Observable<Integer> getWeatherUpdateOptionCalls();
    Consumer<Integer> getWeatherUpdateOptionConsumer();
}
