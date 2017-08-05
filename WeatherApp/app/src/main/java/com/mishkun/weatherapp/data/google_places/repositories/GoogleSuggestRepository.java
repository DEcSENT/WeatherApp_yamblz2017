package com.mishkun.weatherapp.data.google_places.repositories;
/*
 * Created by DV on Space 5 
 * 25.07.2017
 */

import com.mishkun.weatherapp.data.google_places.GooglePlacesApi;
import com.mishkun.weatherapp.data.google_places.detailCityInfo.LocationCity;
import com.mishkun.weatherapp.domain.entities.City;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.CitiesSuggest;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;


import java.util.List;
import java.util.Locale;

import io.reactivex.Single;

import static com.mishkun.weatherapp.Constants.API_KEY_GOOGLE;

public class GoogleSuggestRepository implements SuggestRepository {

    private final GooglePlacesApi googlePlacesApi;
    //private static final String API_KEY_GOOGLE = "AIzaSyCnAOvg2liBhZVM72RQB8k201ehUYv4AMc";

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
        return googlePlacesApi.getDetailPlaceInfo(cityID, API_KEY_GOOGLE, Locale.getDefault().toString())
                .map((coords) -> {
                    LocationCity loc = coords.getResult().getGeometry().getLocationCity();
                    //Log.v("CITY NAME, WTF?!", coords.getResult().getName());
                    return new City(coords.getResult().getName(), new Location(loc.getLat(), loc.getLng()));
                });
    }
}
