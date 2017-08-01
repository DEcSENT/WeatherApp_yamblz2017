package com.mishkun.weatherapp.domain.outerworld;

import io.reactivex.Completable;

public interface WeatherUpdatesScheduler {
    Completable scheduleWeatherUpdates(long millis);
}
