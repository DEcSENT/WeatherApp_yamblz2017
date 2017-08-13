package com.mishkun.weatherapp.presentation.suggest;
/*
 * Created by DV on Space 5 
 * 24.07.2017
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;

import java.util.List;

public class SuggestRecyclerAdapter extends RecyclerView.Adapter<SuggestRecyclerAdapter.MyViewHolder> {

    private List<Prediction> predictionList;
    SuggestFragment.onClickRecyclerItem onClickRecyclerItem;

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView cityTextView;
        private TextView countryTextView;
        MyViewHolder(View view) {
            super(view);
            cityTextView = (TextView) view.findViewById(R.id.cityTextView);
            countryTextView = (TextView) view.findViewById(R.id.countryTextView);
        }
    }

    SuggestRecyclerAdapter(List<Prediction> list, SuggestFragment.onClickRecyclerItem onClickRecyclerItem) {
        this.predictionList = list;
        this.onClickRecyclerItem = onClickRecyclerItem;
    }

    void setNewList(List<Prediction> list) {
        this.predictionList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Prediction pred = predictionList.get(position);
        String predCity = pred.getDescription();
        String city = predCity.contains(",") ? predCity.substring(0, predCity.indexOf(",")) : predCity;
        String country = predCity.contains(",") ? predCity.substring(predCity.indexOf(",")+2, predCity.length()) : predCity;
        holder.cityTextView.setText(city);
        holder.countryTextView.setText(country);
        holder.itemView.setOnClickListener(view -> onClickRecyclerItem.onclick(predictionList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return predictionList.size();
    }
}

