package com.mishkun.weatherapp.domain;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.common.Subscriptable;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public abstract class CompletableInteractor<P> extends Subscriptable {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;


    protected CompletableInteractor(@NonNull Scheduler threadExecutor, @NonNull Scheduler postExecutionThread) {
        super();
        this.jobScheduler = threadExecutor;
        this.uiScheduler = postExecutionThread;
    }


    public abstract Completable buildUseCaseCompletable(P params);

    public Completable run(P params) {
       return this.buildUseCaseCompletable(params)
                                           .subscribeOn(jobScheduler)
                                           .observeOn(uiScheduler);
    }
}
