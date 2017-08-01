package com.mishkun.weatherapp.common;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

abstract public class Subscriptable {
    private final CompositeDisposable subscriptions;

    public Subscriptable() {
        this.subscriptions = new CompositeDisposable();
    }

    protected void dispose() {
        subscriptions.clear();
    }

    protected void addSubscription(@NonNull Disposable disposable) {
        subscriptions.add(disposable);
    }
}
