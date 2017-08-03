package com.mishkun.weatherapp.presentation.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.di.WeatherScreenComponent;
import com.mishkun.weatherapp.presentation.suggest.SuggestFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements WeatherView {
    public static final String TAG = HomeFragment.class.getSimpleName();


    @BindView(R.id.degreesTextView)
    public TextView degreesView;
    @BindView(R.id.humidityTextView)
    public TextView humidityView;
    @BindView(R.id.windTextView)
    public TextView windView;
    @BindView(R.id.pressureTextView)
    public TextView pressureView;
    @BindView(R.id.weatherFragment)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iconWeatherImageView)
    public ImageView imageView;
    @BindView(R.id.cityTextView)
    public TextView cityTextView;
    @Inject
    public WeatherRxPresenter weatherRxPresenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HasComponent<WeatherScreenComponent>) getActivity()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.home_title);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        weatherRxPresenter.attachView(this);
        swipeRefreshLayout.setBackground(getResources().getDrawable(weatherRxPresenter.getBackground()));
    }

    @Override
    public void onPause() {
        super.onPause();
        weatherRxPresenter.detachView();
    }

    @Override
    public Observable<Object> getRefreshCalls() {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout);
    }


    @Override
    public Consumer<WeatherViewModel> getWeatherConsumer() {
        return weather -> {
            humidityView.setText(weather.getHumidityText());
            degreesView.setText(weather.getDegreesText());
            pressureView.setText(weather.getPressureText());
            windView.setText(weather.getWindText());
            imageView.setBackgroundResource(weather.getIconResource());
            cityTextView.setText(weather.getCityName());
        };
    }

    @Override
    public Consumer<String> getErrorConsumer() {
        return s -> Toast.makeText(getActivity(), s,
                Toast.LENGTH_LONG).show();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<Boolean> getLoadingStatusConsumer() {
        return (Consumer<Boolean>) RxSwipeRefreshLayout.refreshing(swipeRefreshLayout);
    }

    @OnClick(R.id.cityTextView)
    public void giveMeSuggest(){
        Log.v("CityName", "CLick!");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, new SuggestFragment(), "SuggestFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
