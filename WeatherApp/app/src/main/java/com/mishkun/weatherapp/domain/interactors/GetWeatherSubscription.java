package com.mishkun.weatherapp.domain.interactors;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.domain.ParameterlessInteractor;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.outerworld.CurrentWeatherProvider;
import com.mishkun.weatherapp.data.google_places.repositories.CityInfoRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static com.mishkun.weatherapp.di.NamedConsts.JOB;
import static com.mishkun.weatherapp.di.NamedConsts.UI;

public class GetWeatherSubscription extends ParameterlessInteractor<Weather> {
    private final CurrentWeatherProvider currentWeatherProvider;
    private final CityInfoRepository cityInfoRepository;

    @Inject
    public GetWeatherSubscription(@NonNull @Named(JOB) Scheduler threadExecutor, @NonNull @Named(UI) Scheduler postExecutionThread,
                                  @NonNull CurrentWeatherProvider currentWeatherProvider, CityInfoRepository cityInfoRepository) {
        super(threadExecutor, postExecutionThread);
        this.currentWeatherProvider = currentWeatherProvider;
        this.cityInfoRepository = cityInfoRepository;
    }

    @Override
    public Observable<Weather> buildUseCaseObservable() {
        return currentWeatherProvider.getCurrentWeatherSubscription()
                .flatMap((weather) -> cityInfoRepository.getCityName()
                        .toObservable()
                        .map((name) -> new Weather(weather.getTemperature(),
                                weather.getHumidity(),
                                weather.getPressureMmHg(),
                                weather.getWeatherConditions(),
                                weather.getWindSpeed(),
                                weather.getTimestamp(),
                                name)));
    }
}
