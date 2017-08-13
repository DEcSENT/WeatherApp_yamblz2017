package com.mishkun.weatherapp;
/*
 * Created by DV on Space 5 
 * 03.08.2017
 */

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mishkun.weatherapp.presentation.AboutFragment;
import com.mishkun.weatherapp.presentation.favourite.FavouriteFragment;
import com.mishkun.weatherapp.presentation.home.HomeFragment;
import com.mishkun.weatherapp.presentation.settings.SettingsFragment;
import com.mishkun.weatherapp.presentation.suggest.SuggestFragment;

public class HomePresenter implements HomePres {

    private HomeActivity view;
    private FragmentManager fragmentManager;

    public HomePresenter(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void attachView(HomeActivity view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void openWeatherFragment() {
        showFragment(new HomeFragment());
    }

    @Override
    public void openSettingsFragment() {
        showFragment(new SettingsFragment());
    }

    @Override
    public void openAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.setStyle( DialogFragment.STYLE_NORMAL, android.R.style.Theme );
        aboutFragment.show(fragmentManager, AboutFragment.TAG);
    }

    @Override
    public void openSelectCityFragment() {
        showFragment(new FavouriteFragment());
    }

    @Override
    public void openSuggestFragment() {
        SuggestFragment suggestFragment = new SuggestFragment();
        suggestFragment.setStyle( DialogFragment.STYLE_NORMAL, android.R.style.Theme );
        suggestFragment.show(fragmentManager, SuggestFragment.TAG);
    }

    private void showFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
