package com.mishkun.weatherapp.domain;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.common.Subscriptable;

import dagger.Subcomponent;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public abstract class SingleInteractor<P, R> {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;


    protected SingleInteractor(@NonNull Scheduler threadExecutor, @NonNull Scheduler postExecutionThread) {
        super();
        this.jobScheduler = threadExecutor;
        this.uiScheduler = postExecutionThread;
    }


    public abstract Single<R> buildUseCaseObservable(P params);

    public Single<R> run(P params) {
        return this.buildUseCaseObservable(params)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);
    }
}
