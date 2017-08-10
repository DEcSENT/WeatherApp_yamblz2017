package com.mishkun.weatherapp.domain.interactors;


import com.mishkun.weatherapp.domain.interactors.GetSuggests;
import com.mishkun.weatherapp.data.google_places.repositories.SuggestRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/*
 * Created by DV on Space 5 
 * 30.07.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class GetSuggestsTest {
    private GetSuggests getSuggests;
    @Mock
    private SuggestRepository suggestRepository;
    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        getSuggests = new GetSuggests(testScheduler, testScheduler, suggestRepository);
    }

    @Test
    public void buildUseCaseObservable() throws Exception {
        getSuggests.buildUseCaseObservable(anyString());
        verify(suggestRepository).getCitiesSuggest(anyString());
    }
}