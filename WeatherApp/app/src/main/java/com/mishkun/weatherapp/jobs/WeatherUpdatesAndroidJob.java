package com.mishkun.weatherapp.jobs;

import android.content.Context;

import com.evernote.android.job.JobManager;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;
import com.mishkun.weatherapp.domain.outerworld.WeatherUpdatesScheduler;

import io.reactivex.Completable;

public class WeatherUpdatesAndroidJob implements WeatherUpdatesScheduler {

    public WeatherUpdatesAndroidJob(UpdateWeather updateWeather, Context context) {
        JobManager.create(context);
        JobManager.instance().addJobCreator(new WeatherJobCreator(updateWeather));
    }

    @Override
    public Completable scheduleWeatherUpdates(long millis) {
        JobManager.instance().cancelAll();
        WeatherJob.scheduleJob(millis);
        return Completable.complete();
    }
}
