package com.mishkun.weatherapp.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;

import io.reactivex.annotations.Nullable;

public class WeatherJobCreator implements JobCreator {

    private final UpdateWeather updateWeather;

    WeatherJobCreator(UpdateWeather updateWeather) {
        this.updateWeather = updateWeather;
    }

    @Override
    public @Nullable
    Job create(String tag) {
        switch (tag) {
            case WeatherJob.TAG:
                return new WeatherJob(updateWeather);
            default:
                return null;
        }
    }
}
