package com.mishkun.weatherapp.presentation.favourite;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import android.util.Log;

import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.domain.interactors.ApplyCityInfo;
import com.mishkun.weatherapp.domain.interactors.GetSuggests;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class FavouritePresenter {

    private FavouriteFragment view;
    private final ApplyCityInfo applyCityInfo;
    private final GetSuggests getSuggests;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public FavouritePresenter(ApplyCityInfo applyCityInfo, GetSuggests getSuggests) {
        this.applyCityInfo = applyCityInfo;
        this.getSuggests = getSuggests;
    }

    protected void onAttach(FavouriteFragment view) {
        this.view = view;
    }

    protected void onDetach() {
        this.view = null;
        disposables.clear();
    }

    public void getCitiesList(){
        disposables.add(applyCityInfo.getCities()
                .subscribe(new Consumer<List<CityEntity>>() {
                    @Override
                    public void accept(List<CityEntity> cityEntities) throws Exception {
                        view.setFavouriteCities(cityEntities);
                        for(CityEntity i : cityEntities){
                            Log.v("DATABASE------CITY:", i.getCityName() + " LAN: " + i.getLatitude() + " LON: "+ i.getLongitude() + " ID: " + i.getUid());
                        }
                    }
                }));
    }

    public void setFavouriteCity(String cityName, String lat, String favourite){
        applyCityInfo.setFavouriteCityIntoDB(cityName, lat, favourite);
    }

    public void deleteCity(int cityPosition){
        applyCityInfo.deleteCity(cityPosition).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                view.showMessage("Город удален");
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage("Ошибка удаления");
            }
        });
    }
}
