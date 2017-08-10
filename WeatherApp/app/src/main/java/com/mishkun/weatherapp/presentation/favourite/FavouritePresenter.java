package com.mishkun.weatherapp.presentation.favourite;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import com.mishkun.weatherapp.domain.interactors.ApplyCityInfo;
import com.mishkun.weatherapp.domain.interactors.GetSuggests;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FavouritePresenter {

    private FavouriteFragment view;
    private final ApplyCityInfo applyCityInfo;
    private final GetSuggests getSuggests;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    FavouritePresenter(ApplyCityInfo applyCityInfo, GetSuggests getSuggests) {
        this.applyCityInfo = applyCityInfo;
        this.getSuggests = getSuggests;
    }

    void onAttach(FavouriteFragment view) {
        this.view = view;
    }

    void onDetach() {
        this.view = null;
        disposables.clear();
    }

    void getCitiesList(){
        disposables.add(applyCityInfo.getCities()
                .subscribe(cityEntities -> view.setFavouriteCities(cityEntities)));
    }

    void setFavouriteCity(String cityName, String lat, String favourite){
        applyCityInfo.setFavouriteCityIntoDB(cityName, lat, favourite);
    }

    void deleteCity(int cityPosition){
        applyCityInfo.deleteCity(cityPosition).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                view.showMessage(1);
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage(0);
            }
        });
    }
}
