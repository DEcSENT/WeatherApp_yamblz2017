package com.mishkun.weatherapp.presentation.favourite;

import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.domain.interactors.ApplyCityInfo;
import com.mishkun.weatherapp.domain.interactors.GetSuggests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 09.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class FavouritePresenterTest {
    private FavouritePresenter favouritePresenter;
    @Mock
    private ApplyCityInfo applyCityInfo;
    @Mock
    private GetSuggests getSuggests;
    @Mock
    private FavouriteFragment view;


    @Before
    public void setUp() throws Exception {
        favouritePresenter = new FavouritePresenter(applyCityInfo, getSuggests);
        favouritePresenter.onAttach(view);

//        List<CityEntity> list = new ArrayList<>();
//        CityEntity cityEntity = new CityEntity();
//        cityEntity.setCityName("sdfsdfsdf");
//        list.add(cityEntity);
//
//        when(applyCityInfo.getCities()).thenReturn(Flowable.just(list));
        when(applyCityInfo.deleteCity(anyInt())).thenReturn(Completable.complete());

        doNothing().when(view).showMessage(anyInt());
    }

//    @Test
//    public void getCitiesList() throws Exception {
//        favouritePresenter.getCitiesList();
//        verify(applyCityInfo).getCities();
//    }

    @Test
    public void setFavouriteCity() throws Exception {
        favouritePresenter.setFavouriteCity(anyString(), anyString(), anyString());
        verify(applyCityInfo).setFavouriteCityIntoDB(anyString(), anyString(), anyString());
    }

    @Test
    public void deleteCity() throws Exception {
        favouritePresenter.deleteCity(anyInt());
        verify(applyCityInfo).deleteCity(anyInt());
    }
}