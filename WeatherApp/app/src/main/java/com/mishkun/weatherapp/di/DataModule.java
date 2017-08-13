package com.mishkun.weatherapp.di;

import com.mishkun.weatherapp.data.current_weather.OpenWeatherMapApi;
import com.mishkun.weatherapp.data.current_weather.OpenWeatherMapCurrentWeatherRepository;
import com.mishkun.weatherapp.data.forecast.OpenWeatherForecastRepository;
import com.mishkun.weatherapp.data.google_places.GooglePlacesApi;
import com.mishkun.weatherapp.data.google_places.repositories.CitiesRepository;
import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.domain.outerworld.CurrentWeatherProvider;
import com.mishkun.weatherapp.data.google_places.repositories.CityInfoRepository;
import com.mishkun.weatherapp.data.google_places.repositories.GoogleSuggestRepository;
import com.mishkun.weatherapp.data.google_places.repositories.SuggestRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class DataModule {

    private static final String BASE_URL_GOOGLE = "https://maps.googleapis.com/";
    private static final String BASE_URL_OPENWEATHER = "http://api.openweathermap.org/data/2.5/";

    @Provides
    @Singleton
    OpenWeatherMapApi provideOpenWeatherMapApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_OPENWEATHER).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApi.class);
    }

    @Provides
    @Singleton GooglePlacesApi provideGooglePlacesApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_GOOGLE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GooglePlacesApi.class);
    }

    @Provides
    @Singleton
    SuggestRepository provideSuggestRepository(GooglePlacesApi googlePlacesApi){
        return new GoogleSuggestRepository(googlePlacesApi);
    }

    @Provides
    @Singleton
    CurrentWeatherProvider provideCurrentWeather(OpenWeatherMapApi openWeatherMapApi, DataBase dataBase) {
        return new OpenWeatherMapCurrentWeatherRepository(openWeatherMapApi, dataBase, Schedulers.io());
    }

    @Provides
    @Singleton
    CityInfoRepository provideCityInfo(DataBase dataBase){
        return new CitiesRepository(dataBase, Schedulers.io());
    }

    @Provides
    @Singleton
    OpenWeatherForecastRepository openWeatherForecastRepository(DataBase dataBase, OpenWeatherMapApi openWeatherMapApi){
        return new OpenWeatherForecastRepository(dataBase, openWeatherMapApi, Schedulers.io());
    }
}
