package com.mishkun.weatherapp.infrastructure;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.AppModule;
import com.mishkun.weatherapp.di.DaggerAppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.domain.interactors.GetWeatherUpdatesFrequency;
import com.mishkun.weatherapp.domain.interactors.ScheduleWeatherUpdate;

import javax.inject.Inject;

public class WeatherApplication extends Application implements HasComponent<AppComponent> {

    private AppComponent appComponent;
    private ScheduleWeatherUpdate scheduleWeatherUpdate;
    private GetWeatherUpdatesFrequency getWeatherUpdatesFrequency;

    @Inject
    public void Inject(ScheduleWeatherUpdate scheduleWeatherUpdate, GetWeatherUpdatesFrequency getWeatherUpdatesFrequency){
        this.scheduleWeatherUpdate = scheduleWeatherUpdate;
        this.getWeatherUpdatesFrequency = getWeatherUpdatesFrequency;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.appComponent = DaggerAppComponent.builder()
                                              .appModule(new AppModule(this))
                                              .build();
        appComponent.inject(this);
        rescheduleWeatherJob();

        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }

    private void rescheduleWeatherJob() {
        getWeatherUpdatesFrequency.run().flatMapCompletable(scheduleWeatherUpdate::run).subscribe();
    }


    @Override
    public AppComponent getComponent() {
        return appComponent;
    }
}
