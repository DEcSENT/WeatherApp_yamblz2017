package com.mishkun.weatherapp.presentation.home;
/*
 * Created by DV on Space 5 
 * 08.08.2017
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.db.ForecastEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.MyViewHolder> {

    private List<ForecastEntity> forecastWeatherList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dateForecastTextView;
        private ImageView iconForecastImageView;
        private TextView tempForecastTextView;

        public MyViewHolder(View view) {
            super(view);
            dateForecastTextView = (TextView) view.findViewById(R.id.dateForecastTextView);
            iconForecastImageView = (ImageView) view.findViewById(R.id.iconForecastImageView);
            tempForecastTextView = (TextView) view.findViewById(R.id.tempForecastTextView);
        }
    }

    public ForecastRecyclerAdapter(List<ForecastEntity> weatherList) {
        this.forecastWeatherList = weatherList;
    }

    public void setNewList(List<ForecastEntity> weatherList) {
        this.forecastWeatherList = weatherList;
    }


    @Override
    public ForecastRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false);
        return new ForecastRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastRecyclerAdapter.MyViewHolder holder, final int position) {
        final ForecastEntity forecast = forecastWeatherList.get(position);
        holder.dateForecastTextView.setText(getDate(forecast.getDt()));
        holder.iconForecastImageView.setBackgroundResource(codeToWeatherIcon(forecast.getWeather()));
        holder.tempForecastTextView.setText(String.format(Locale.getDefault(),"%.0f°", forecast.getTemp() - 273));
    }

    @Override
    public int getItemCount() {
        return forecastWeatherList.size();
    }

    private String getDate(int dateMills){
        Date date = new Date(dateMills*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM", Locale.getDefault());
        return sdf.format(date);
    }

    private static int codeToWeatherIcon(int code) {
        if (code >= 200 && code < 300) {
            return R.drawable.thunderstorm;
        } else if (code >= 300 && code < 400) {
            return R.drawable.drizzle;
        } else if (code >= 500 && code < 600) {
            return R.drawable.rain;
        } else if (code >= 600 && code < 700) {
            return R.drawable.snowflake;
        } else if (code >= 700 && code < 800) {
            return R.drawable.fog;
        } else if (code >= 800 && code < 803) {
            return R.drawable.partly_cloudy;
        } else if (code >= 803 && code < 900) {
            return R.drawable.cloudy;
        } else {
            return R.drawable.sun;
        }
    }
}
