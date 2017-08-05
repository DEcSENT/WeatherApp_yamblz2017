package com.mishkun.weatherapp.domain.interactors;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.domain.SingleInteractor;
import com.mishkun.weatherapp.domain.SingleParameterlessInteractor;
import com.mishkun.weatherapp.domain.outerworld.UpdatePreferenceProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.mishkun.weatherapp.di.NamedConsts.JOB;
import static com.mishkun.weatherapp.di.NamedConsts.UI;

public class GetWeatherUpdatesFrequency extends SingleParameterlessInteractor<Long> {
    private final UpdatePreferenceProvider updatePreferenceProvider;

    @Inject
    public GetWeatherUpdatesFrequency(@NonNull @Named(JOB) Scheduler threadExecutor, @NonNull @Named(UI) Scheduler postExecutionThread,
                                      @NonNull UpdatePreferenceProvider updatePreferenceProvider) {
        super(threadExecutor, postExecutionThread);
        this.updatePreferenceProvider = updatePreferenceProvider;
    }

    @Override
    public Single<Long> buildUseCaseObservable() {
        return updatePreferenceProvider.getUpdateFrequency();
    }
}
