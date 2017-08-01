package com.mishkun.weatherapp.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mishkun.weatherapp.domain.outerworld.UpdatePreferenceProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UpdateSharedPreferenceProvider implements UpdatePreferenceProvider {
    private static final String KEY_WEATHER_UPDATE = "WEATHER_UPDATE";
    private final Context context;
    private final SharedPreferences prefs;

    public UpdateSharedPreferenceProvider(Context context) {
        this.context = context;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Single<Long> getUpdateFrequency() {
        return Single.fromCallable(() -> prefs.getLong(KEY_WEATHER_UPDATE, TimeUnit.MINUTES.toMillis(15)));
    }

    @Override
    public Completable setUpdateFrequency(long frequency) {
        return Completable.fromAction(() -> prefs.edit().putLong(KEY_WEATHER_UPDATE, frequency).apply());
    }
}
