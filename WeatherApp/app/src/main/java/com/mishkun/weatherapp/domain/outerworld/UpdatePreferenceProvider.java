package com.mishkun.weatherapp.domain.outerworld;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UpdatePreferenceProvider {
    Single<Long> getUpdateFrequency();
    Completable setUpdateFrequency(long frequency);
}
