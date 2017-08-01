package com.mishkun.weatherapp.data.google_places.repositories;
/*
 * Created by DV on Space 5 
 * 25.07.2017
 */

import android.util.Log;

import com.mishkun.weatherapp.data.google_places.GooglePlacesApi;
import com.mishkun.weatherapp.domain.entities.City;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.CitiesSuggest;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;


import java.util.List;

import io.reactivex.Single;

public class GoogleSuggestRepository implements SuggestRepository {

    private final GooglePlacesApi googlePlacesApi;
    private static final String API_KEY_GOOGLE = "AIzaSyCnAOvg2liBhZVM72RQB8k201ehUYv4AMc";

    public GoogleSuggestRepository(GooglePlacesApi googlePlacesApi) {
        this.googlePlacesApi = googlePlacesApi;
    }

    @Override
    public Single<List<Prediction>> getCitiesSuggest(String string) {
        return googlePlacesApi.getSuggest(string, "(cities)", API_KEY_GOOGLE)
                .map(CitiesSuggest::getPredictions);
    }

    @Override
    public Single<City> getCityCoordinates(String cityID) {
        return googlePlacesApi.getDetailPlaceInfo(cityID, API_KEY_GOOGLE)
                .map((coords) -> {
                    com.mishkun.weatherapp.data.google_places.detailCityInfo.Location loc = coords.getResult().getGeometry().getLocation();
                    //Log.v("CITY NAME, WTF?!", coords.getResult().getName());
                    return new City(coords.getResult().getName(), new Location(loc.getLat(), loc.getLng()));
                });
    }
}
