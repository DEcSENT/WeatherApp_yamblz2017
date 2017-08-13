package com.mishkun.weatherapp.jobs;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;

import java.util.concurrent.TimeUnit;

public class WeatherJob extends Job {
    static final String TAG = "WeatherJob";
    private final UpdateWeather updateWeather;

    WeatherJob(UpdateWeather updateWeather) {
        this.updateWeather = updateWeather;
    }

    static int scheduleJob(long millis) {
        return new JobRequest.Builder(TAG)
                .setPersisted(true)
                .setPeriodic(millis, TimeUnit.MINUTES.toMillis(10))
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        updateWeather.run().onErrorComplete().subscribe();
        return Result.SUCCESS;
    }
}
