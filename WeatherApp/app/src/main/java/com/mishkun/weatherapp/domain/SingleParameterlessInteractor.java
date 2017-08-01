package com.mishkun.weatherapp.domain;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.common.Subscriptable;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public abstract class SingleParameterlessInteractor<R> extends Subscriptable {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;


    protected SingleParameterlessInteractor(@NonNull Scheduler threadExecutor, @NonNull Scheduler postExecutionThread) {
        super();
        this.jobScheduler = threadExecutor;
        this.uiScheduler = postExecutionThread;
    }

    public abstract Single<R> buildUseCaseObservable();

    public Single<R> run() {
        return this.buildUseCaseObservable()
                   .subscribeOn(jobScheduler)
                   .observeOn(uiScheduler);
    }
}
