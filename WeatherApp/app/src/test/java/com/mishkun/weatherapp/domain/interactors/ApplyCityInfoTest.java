package com.mishkun.weatherapp.domain.interactors;

import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;
import com.mishkun.weatherapp.data.google_places.repositories.CityInfoRepository;
import com.mishkun.weatherapp.data.google_places.repositories.SuggestRepository;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.domain.entities.City;
import com.mishkun.weatherapp.domain.entities.Location;

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
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 08.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplyCityInfoTest {
    private ApplyCityInfo applyCityInfo;
    @Mock
    private SuggestRepository suggestRepository;
    @Mock
    private CityInfoRepository cityInfoRepository;
    private TestScheduler testScheduler;
    @Before
    public void setUp() throws Exception {
        applyCityInfo = new ApplyCityInfo(testScheduler, testScheduler, suggestRepository, cityInfoRepository);
        Location location = new Location(10.0, 10.0);
        City city = new City("Moscow", location);
        List<CityEntity> cityEntityList = new ArrayList<>();
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityName("Test");
        cityEntityList.add(cityEntity);

        when(suggestRepository.getCityCoordinates(anyString())).thenReturn(Single.just(city));
        when(cityInfoRepository.getCitiesList()).thenReturn(Flowable.just(cityEntityList));
    }
    @Test
    public void buildUseCaseCompletable() throws Exception {
        applyCityInfo.buildUseCaseCompletable("test");
        verify(suggestRepository).getCityCoordinates("test");
    }

    @Test
    public void getCities() throws Exception {
        applyCityInfo.getCities();
        verify(cityInfoRepository).getCitiesList();
    }

    @Test
    public void setFavouriteCityIntoDB() throws Exception {
        applyCityInfo.setFavouriteCityIntoDB(anyString(), anyString(), anyString());
        verify(cityInfoRepository).setFavourite(anyString(), anyString(), anyString());
    }

    @Test
    public void deleteCity() throws Exception {
        applyCityInfo.deleteCity(anyInt());
        verify(cityInfoRepository).deleteCity(anyInt());
    }
}