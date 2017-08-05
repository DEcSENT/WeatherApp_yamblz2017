package com.mishkun.weatherapp.presentation.settings;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.di.SettingsScreenComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class SettingsFragment extends Fragment implements SettingsView {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    @BindView(R.id.refresh_weather_spinner)
    public AppCompatSpinner spinner;
    private SettingsScreenComponent settingsScreenComponent;

    @Inject
    public SettingsRxPresenter settingsPresenter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsScreenComponent = ((HasComponent<AppComponent>) getActivity().getApplication()).getComponent().settingsScreenComponent();
        settingsScreenComponent.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.settings_title);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsPresenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        settingsPresenter.detachView();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        settingsScreenComponent = null;
    }
    @Override
    public Observable<Integer> getWeatherUpdateOptionCalls() {
        return RxAdapterView.itemSelections(spinner).skipInitialValue().skip(1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<Integer> getWeatherUpdateOptionConsumer() {
        return (Consumer<Integer>) RxAdapterView.selection(spinner);
    }
}
