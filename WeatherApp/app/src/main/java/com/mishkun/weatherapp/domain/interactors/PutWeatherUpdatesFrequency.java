package com.mishkun.weatherapp.domain.interactors;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.domain.CompletableInteractor;
import com.mishkun.weatherapp.domain.outerworld.UpdatePreferenceProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.mishkun.weatherapp.di.NamedConsts.JOB;
import static com.mishkun.weatherapp.di.NamedConsts.UI;

public class PutWeatherUpdatesFrequency extends CompletableInteractor<Long> {
    private final UpdatePreferenceProvider updatePreferenceProvider;

    @Inject
    public PutWeatherUpdatesFrequency(@NonNull @Named(JOB) Scheduler threadExecutor, @NonNull @Named(UI) Scheduler postExecutionThread,
                                      @NonNull UpdatePreferenceProvider updatePreferenceProvider) {
        super(threadExecutor, postExecutionThread);
        this.updatePreferenceProvider = updatePreferenceProvider;
    }

    @Override
    public Completable buildUseCaseCompletable(Long params) {
        return updatePreferenceProvider.setUpdateFrequency(params);
    }
}
