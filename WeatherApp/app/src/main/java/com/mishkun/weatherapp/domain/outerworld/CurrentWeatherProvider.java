package com.mishkun.weatherapp.domain.outerworld;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Weather;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CurrentWeatherProvider {
    Completable refreshData(@NonNull Location location);
    Observable<Weather> getCurrentWeatherSubscription();
}
