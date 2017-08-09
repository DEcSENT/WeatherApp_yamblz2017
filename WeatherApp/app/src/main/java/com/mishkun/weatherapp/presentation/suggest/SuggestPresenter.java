package com.mishkun.weatherapp.presentation.suggest;
/*
 * Created by DV on Space 5 
 * 24.07.2017
 */

import com.mishkun.weatherapp.domain.interactors.ApplyCityInfo;
import com.mishkun.weatherapp.domain.interactors.GetSuggests;

import javax.inject.Inject;

public class SuggestPresenter {

    private SuggestView view;
    private final ApplyCityInfo applyCityInfo;
    private final GetSuggests getSuggests;

    @Inject
    public SuggestPresenter(ApplyCityInfo applyCityInfo, GetSuggests getSuggests) {
        this.applyCityInfo = applyCityInfo;
        this.getSuggests = getSuggests;
    }

    protected void onAttach(SuggestView view) {
        this.view = view;
    }

    protected void onDetach() {
        this.view = null;
    }

    public void getSuggestFromWeb(String string) {
        getSuggests.run(string).subscribe((list) -> view.setSuggestAdapter(list),
                (error) -> view.showError(error.getLocalizedMessage()));
    }

    public void getCityCoordinatesFromWeb(String cityID) {
        //TODO: Try to fix NPE here<-------------------------------------
        applyCityInfo.run(cityID)
                .subscribe(() -> view.terminateFragment(),
                            (error) -> view.showError("Error"));
    }
}
