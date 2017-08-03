package com.mishkun.weatherapp.data.google_places;
/*
 * Created by DV on Space 5 
 * 24.07.2017
 */

import com.mishkun.weatherapp.data.google_places.citiesSuggest.CitiesSuggest;
import com.mishkun.weatherapp.data.google_places.detailCityInfo.DetailCityInfo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesApi {
    @GET("maps/api/place/autocomplete/json")
    Single<CitiesSuggest> getSuggest(@Query("input") String input, @Query("types") String types, @Query("key") String key);

    @GET("maps/api/place/details/json")
    Single<DetailCityInfo> getDetailPlaceInfo(@Query("placeid") String placeid, @Query("key") String key, @Query("language") String lang);
}
