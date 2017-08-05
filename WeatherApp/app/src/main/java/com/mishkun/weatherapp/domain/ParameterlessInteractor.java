package com.mishkun.weatherapp.domain;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.common.Subscriptable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public abstract class ParameterlessInteractor<R> extends Subscriptable {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;


    protected ParameterlessInteractor(@NonNull Scheduler threadExecutor, @NonNull Scheduler postExecutionThread) {
        super();
        this.jobScheduler = threadExecutor;
        this.uiScheduler = postExecutionThread;
    }


    public abstract Observable<R> buildUseCaseObservable();

    public Observable<R> run() {
        return this.buildUseCaseObservable()
                   .subscribeOn(jobScheduler)
                   .observeOn(uiScheduler);
    }
}