package com.mishkun.weatherapp.presentation.home;

import android.util.Log;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.WeatherScreen;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.interactors.ApplyCityInfo;
import com.mishkun.weatherapp.domain.interactors.GetWeatherSubscription;
import com.mishkun.weatherapp.domain.interactors.UpdateWeather;
import com.mishkun.weatherapp.presentation.RxPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.Completable;

@WeatherScreen
public class WeatherRxPresenter extends RxPresenter<WeatherView> {
    private static final String TAG = WeatherRxPresenter.class.getSimpleName();
    private final GetWeatherSubscription getWeatherSubscription;
    private final UpdateWeather updateWeather;
    private final WeatherMapper weatherMapper;
    private BehaviorRelay<Weather> weatherStatus;
    private BehaviorRelay<Boolean> loadingStatus;
    private PublishRelay<String> errorMessages;

    @Inject
    WeatherRxPresenter(GetWeatherSubscription getWeatherSubscription, UpdateWeather updateWeather,
                       WeatherMapper weatherMapper) {
        this.getWeatherSubscription = getWeatherSubscription;
        this.weatherMapper = weatherMapper;
        weatherStatus = BehaviorRelay.create();
        loadingStatus = BehaviorRelay.createDefault(Boolean.FALSE);
        errorMessages = PublishRelay.create();
        this.updateWeather = updateWeather;
        this.getWeatherSubscription.run().subscribe(weatherStatus);

        // MAYBE HERE WILL BE SUBSCRIBE TO REPOSITORY WITH LOCATION? OR INTERACTOR?
    }

    @Override
    protected void onAttach() {
        Completable weatherRefreshSubscription = view.getRefreshCalls()
                .filter((ignore) -> !loadingStatus.getValue())
                .doOnNext((ignore) -> loadingStatus.accept(true))
                .flatMapCompletable((ignore) -> updateWeather.run()
                        .doOnError((error) -> errorMessages.accept(error.getLocalizedMessage()))
                        .onErrorComplete()
                        .doFinally(() -> loadingStatus.accept(false)));
        addSubscription(weatherRefreshSubscription.subscribe());
        addSubscription(updateWeather.run()
                .doOnError((error) -> errorMessages.accept(error.getLocalizedMessage()))
                .onErrorComplete()
                .subscribe());
        addSubscription(weatherStatus.hide().map(weatherMapper::toWeatherViewModel).subscribe(view.getWeatherConsumer()));
        addSubscription(loadingStatus.subscribe(view.getLoadingStatusConsumer()));
        addSubscription(errorMessages.subscribe(view.getErrorConsumer()));
    }

    @Override
    protected void onDetach() {

    }

    int getBackground() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        int currentHour = Integer.parseInt(sdf.format(date));
        Log.v("time", currentHour + "");
        if (currentHour > 0 & currentHour < 6) {
            return R.drawable.gradient_night;
        } else if (currentHour >= 6 & currentHour <= 10) {
            return R.drawable.gradient_morning;
        } else if (currentHour > 10 & currentHour <= 19) {
            return R.drawable.gradient_day;
        } else if (currentHour > 19) {
            return R.drawable.gradient_evening;
        } else {
            return R.color.colorAccent;
        }
    }
}
