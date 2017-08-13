package com.mishkun.weatherapp.data.google_places.repositories;

import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.db.CityEntityDAO;
import com.mishkun.weatherapp.db.DataBase;
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
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 10.08.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class CitiesRepositoryTest {
    private CitiesRepository citiesRepository;

    @Mock
    private DataBase dataBase;
    @Mock
    private CityEntityDAO cityEntityDAO;

    private CityEntity cityEntity = new CityEntity();
    private TestScheduler testScheduler;
    private List<CityEntity> entityList;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        citiesRepository = new CitiesRepository(dataBase, testScheduler);

        cityEntity.setCityName("test");
        cityEntity.setLatitude(String.valueOf(10));
        cityEntity.setLongitude(String.valueOf(10));
        cityEntity.setFavourite("0");

        entityList = new ArrayList<>();
        entityList.add(cityEntity);

        when(dataBase.cityDao()).thenReturn(cityEntityDAO);

        when(cityEntityDAO.getFavourite()).thenReturn(Single.just(cityEntity));

        when(cityEntityDAO.getAllCities()).thenReturn(Flowable.just(entityList));

    }

    //TODO - start this shit
    @Test
    public void setCityInfo() throws Exception {
        citiesRepository.setCityInfo(new Location(10, 10), "test").subscribe();
        //testScheduler.triggerActions();
        verify(cityEntityDAO).insertCity(any(CityEntity.class));
    }

    @Test
    public void getCityCoordinate() throws Exception {
        citiesRepository.getCityCoordinate();
        verify(dataBase.cityDao()).getFavourite();
    }

    @Test
    public void getCityName() throws Exception {
        citiesRepository.getCityName();
        verify(dataBase.cityDao()).getFavourite();
    }

    @Test
    public void getCitiesList() throws Exception {
        citiesRepository.getCitiesList();
        verify(dataBase.cityDao()).getAllCities();
    }

    @Test
    public void setFavourite() throws Exception {
        citiesRepository.setFavourite("test", "test", "test");
        testScheduler.triggerActions();
        verify(cityEntityDAO).resetFavourite();
    }

    @Test
    public void deleteCity() throws Exception {
        citiesRepository.deleteCity(10).subscribe();
        testScheduler.triggerActions();
        verify(cityEntityDAO).delete(10);
    }
}