package com.mishkun.weatherapp.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mishkun.weatherapp.db.DataBase;
import com.mishkun.weatherapp.domain.outerworld.UpdatePreferenceProvider;
import com.mishkun.weatherapp.infrastructure.UpdateSharedPreferenceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    UpdatePreferenceProvider getUpdatePreferenceProvider() {
        return new UpdateSharedPreferenceProvider(context);
    }

    @Provides
    @Singleton
    DataBase provideCityDataBase(){
        return Room.databaseBuilder(context,
                DataBase.class, "citiesDB").build();

    }
}
