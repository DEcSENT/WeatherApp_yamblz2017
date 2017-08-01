package com.mishkun.weatherapp.domain.interactors;

import com.mishkun.weatherapp.domain.CompletableInteractor;
import com.mishkun.weatherapp.domain.outerworld.WeatherUpdatesScheduler;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

import static com.mishkun.weatherapp.di.NamedConsts.JOB;
import static com.mishkun.weatherapp.di.NamedConsts.UI;

public class ScheduleWeatherUpdate extends CompletableInteractor<Long> {

    private final WeatherUpdatesScheduler weatherUpdatesScheduler;

    @Inject
    public ScheduleWeatherUpdate(@NonNull @Named(JOB) Scheduler threadExecutor, @NonNull @Named(UI) Scheduler postExecutionThread,
                                 @NonNull WeatherUpdatesScheduler weatherUpdatesScheduler) {
        super(threadExecutor, postExecutionThread);
        this.weatherUpdatesScheduler = weatherUpdatesScheduler;
    }

    @Override
    public Completable buildUseCaseCompletable(Long params) {
        return weatherUpdatesScheduler.scheduleWeatherUpdates(params);
    }
}
