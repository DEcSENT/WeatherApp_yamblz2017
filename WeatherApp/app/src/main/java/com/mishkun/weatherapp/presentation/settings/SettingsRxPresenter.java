package com.mishkun.weatherapp.presentation.settings;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.mishkun.weatherapp.di.SettingsScreen;
import com.mishkun.weatherapp.domain.interactors.GetWeatherUpdatesFrequency;
import com.mishkun.weatherapp.domain.interactors.PutWeatherUpdatesFrequency;
import com.mishkun.weatherapp.domain.interactors.ScheduleWeatherUpdate;
import com.mishkun.weatherapp.presentation.RxPresenter;

import javax.inject.Inject;

import io.reactivex.Completable;

@SettingsScreen
public class SettingsRxPresenter extends RxPresenter<SettingsView> {

    private final GetWeatherUpdatesFrequency getWeatherUpdatesFrequency;
    private final PutWeatherUpdatesFrequency putWeatherUpdatesFrequency;
    private final ScheduleWeatherUpdate scheduleWeatherUpdate;
    private final SettingsMapper settingsMapper;

    private BehaviorRelay<Integer> selectedMinutes;

    @Inject
    SettingsRxPresenter(GetWeatherUpdatesFrequency getWeatherUpdatesFrequency,
                        PutWeatherUpdatesFrequency putWeatherUpdatesFrequency,
                        ScheduleWeatherUpdate scheduleWeatherUpdate, SettingsMapper settingsMapper) {
        this.getWeatherUpdatesFrequency = getWeatherUpdatesFrequency;
        this.putWeatherUpdatesFrequency = putWeatherUpdatesFrequency;
        this.scheduleWeatherUpdate = scheduleWeatherUpdate;
        this.settingsMapper = settingsMapper;
        this.selectedMinutes = BehaviorRelay.create();
        this.getWeatherUpdatesFrequency.run()
                                       .map(settingsMapper::millisToMinutes)
                                       .subscribe(selectedMinutes);
    }


    @Override
    protected void onAttach() {
        addSubscription(selectedMinutes.hide()
                                       .map(settingsMapper::toViewSpinnerPosition)
                                       .subscribe(view.getWeatherUpdateOptionConsumer()));
        Completable completable = view.getWeatherUpdateOptionCalls()
                                      .map(settingsMapper::toActualMinutes)
                                      .map(settingsMapper::minutesToMillis)
                                      .doOnNext(scheduleWeatherUpdate::run)
                                      .flatMapCompletable(putWeatherUpdatesFrequency::run);
        addSubscription(completable.subscribe());
    }

    @Override
    protected void onDetach() {

    }
}
