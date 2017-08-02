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


    @BindView(R.id.degrees_text_view)
    public TextView degreesView;
    @BindView(R.id.humidity_text_view)
    public TextView humidityView;
    @BindView(R.id.wind_text_view)
    public TextView windView;
    @BindView(R.id.pressure_text_view)
    public TextView pressureView;
    @BindView(R.id.weatherFragment)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.icon_view)
    public ImageView imageView;
    @BindView(R.id.city_text_view)
    public TextView city_text_view;
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
            city_text_view.setText(weather.getCityName());
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

    @OnClick(R.id.city_text_view)
    public void giveMeSuggest(){
        Log.v("CityName", "CLick!");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, new SuggestFragment(), "SuggestFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
