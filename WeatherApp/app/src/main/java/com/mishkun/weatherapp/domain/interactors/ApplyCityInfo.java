package com.mishkun.weatherapp.domain.interactors;

import android.support.annotation.NonNull;

import com.mishkun.weatherapp.domain.CompletableInteractor;
import com.mishkun.weatherapp.data.google_places.repositories.CityInfoRepository;
import com.mishkun.weatherapp.data.google_places.repositories.SuggestRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.mishkun.weatherapp.di.NamedConsts.JOB;
import static com.mishkun.weatherapp.di.NamedConsts.UI;

/*
 * Created by DV on Space 5 
 * 26.07.2017
 */

public class ApplyCityInfo extends CompletableInteractor<String> {

    private final SuggestRepository suggestRepository;
    private final CityInfoRepository cityInfoRepository;

    @Inject
    public ApplyCityInfo(@NonNull @Named(JOB) Scheduler threadExecutor, @NonNull @Named(UI) Scheduler postExecutionThread, SuggestRepository suggestRepository, CityInfoRepository cityInfoRepository) {
        super(threadExecutor, postExecutionThread);
        this.suggestRepository = suggestRepository;
        this.cityInfoRepository = cityInfoRepository;
    }

    @Override
    public Completable buildUseCaseCompletable(String params) {
        return suggestRepository.getCityCoordinates(params)
                .flatMapCompletable((city) -> cityInfoRepository.setCityInfo(city.getLocation(), city.getName()));
    }
}
