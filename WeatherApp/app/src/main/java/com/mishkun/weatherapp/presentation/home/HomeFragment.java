package com.mishkun.weatherapp.presentation.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.db.ForecastEntity;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.di.WeatherScreenComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.swipetoRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.iconWeatherImageView)
    public ImageView imageView;
    @BindView(R.id.cityTextView)
    public TextView cityTextView;
    @Inject
    public WeatherRxPresenter weatherRxPresenter;

    @BindView(R.id.forecastRecyclerView)
    public RecyclerView forecastRecyclerView;
    private ForecastRecyclerAdapter forecastRecyclerAdapter;

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
            supportActionBar.setTitle(R.string.weather_title);
        }

        List<ForecastEntity> forecastEntityList = new ArrayList<>();
        ForecastEntity forecastEntity = new ForecastEntity(1502179800, 100, 100, 0, 250, 50, 10, 10);
        forecastEntityList.add(forecastEntity);

        forecastRecyclerAdapter = new ForecastRecyclerAdapter(forecastEntityList);
        forecastRecyclerView.setAdapter(forecastRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        forecastRecyclerView.setLayoutManager(layoutManager);

        if ((((HomeActivity)getActivity()).getSupportActionBar()) != null) {
            ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        weatherRxPresenter.attachView(this);
        swipeRefreshLayout.setBackground(getResources().getDrawable(weatherRxPresenter.getBackground()));
        weatherRxPresenter.getForecast();
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
        return s -> Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<Boolean> getLoadingStatusConsumer() {
        return (Consumer<Boolean>) RxSwipeRefreshLayout.refreshing(swipeRefreshLayout);
    }

    public void setForecastList(List<ForecastEntity> forecastEntityList){
        forecastRecyclerAdapter.setNewList(forecastEntityList);
        forecastRecyclerView.setAdapter(forecastRecyclerAdapter);
    }

    public void showErrorMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
