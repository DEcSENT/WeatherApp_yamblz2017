package com.mishkun.weatherapp.presentation.suggest;


import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;
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
import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 30.07.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class SuggestPresenterTest {
    private SuggestPresenter suggestPresenter;
    @Mock
    private SuggestView view;
    @Mock
    private ApplyCityInfo applyCityInfo;
    @Mock
    private GetSuggests getSuggests;
    private List<Prediction> arrayList;

    @Before
    public void setUp() throws Exception {
        suggestPresenter = new SuggestPresenter(applyCityInfo, getSuggests);
        suggestPresenter.onAttach(view);
        arrayList = new ArrayList<>();
        Prediction pred = new Prediction("Moscow", "1234", "1234");
        arrayList.add(pred);
        when(getSuggests.run(anyString())).thenReturn(Single.just(arrayList));
        when(applyCityInfo.run(anyString())).thenReturn(Completable.complete());

        when(getSuggests.run("some bad string")).thenReturn(Single.error(new Throwable()));
        when(applyCityInfo.run("some bad id")).thenReturn(Completable.error(new Throwable()));
    }

    @Test
    public void getSuggestFromWeb() throws Exception {
        suggestPresenter.getSuggestFromWeb(anyString());
        verify(view).setSuggestAdapter(arrayList);

    }

    @Test
    public void getCityCoordinatesFromWeb() throws Exception {
        suggestPresenter.getCityCoordinatesFromWeb(anyString());
        verify(view).terminateFragment();
    }

    @Test
    public void SomethingGoingWrong_getSuggestFromWeb() throws Exception {
        suggestPresenter.getSuggestFromWeb("some bad string");
        verify(view).showError(null);
    }

    @Test
    public void SomethingGoingWrong_getCityCoordinatesFromWeb() throws Exception {
        suggestPresenter.getCityCoordinatesFromWeb("some bad id");
        verify(view).showError("Error adding");
    }
}