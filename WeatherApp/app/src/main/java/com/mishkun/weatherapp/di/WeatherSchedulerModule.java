package com.mishkun.weatherapp.di;

import android.content.Context;

import com.mishkun.weatherapp.domain.interactors.UpdateWeather;
import com.mishkun.weatherapp.domain.outerworld.WeatherUpdatesScheduler;
import com.mishkun.weatherapp.jobs.WeatherUpdatesAndroidJob;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class WeatherSchedulerModule {
    @Provides
    @Singleton
    WeatherUpdatesScheduler getWeatherUpdatesScheduler(UpdateWeather updateWeather, Context context) {
        return new WeatherUpdatesAndroidJob(updateWeather, context);
    }
}
