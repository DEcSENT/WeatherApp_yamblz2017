package com.mishkun.weatherapp.presentation.favourite;
/*
 * Created by DV on Space 5 
 * 05.08.2017
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.db.CityEntity;

import java.util.List;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.MyViewHolder> {

    private List<CityEntity> cityEntityList;
    FavouriteFragment.onClickRecyclerItem onClickRecyclerItem;

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView favouriteCityTextView;
        MyViewHolder(View view) {
            super(view);
            favouriteCityTextView = (TextView) view.findViewById(R.id.favouriteCityTextView);
        }
    }

    FavouriteRecyclerAdapter(List<CityEntity> list, FavouriteFragment.onClickRecyclerItem onClickRecyclerItem) {
        this.cityEntityList = list;
        this.onClickRecyclerItem = onClickRecyclerItem;
    }

    void setNewList(List<CityEntity> list) {
        this.cityEntityList = list;
    }

    @Override
    public FavouriteRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_favourite_item, parent, false);
        return new FavouriteRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouriteRecyclerAdapter.MyViewHolder holder, final int position) {
        final CityEntity favCity = cityEntityList.get(position);
        holder.favouriteCityTextView.setText(favCity.getCityName());
        if(cityEntityList.get(position).getFavourite().equals("1")){
            holder.favouriteCityTextView.setBackgroundResource(R.drawable.favourite_city_card_selected);
        } else {
            holder.favouriteCityTextView.setBackgroundResource(R.drawable.favourite_city_card_default);
        }
        holder.itemView.setOnClickListener(view -> onClickRecyclerItem.onclick(cityEntityList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return cityEntityList.size();
    }

    CityEntity getCity(int position){
        return cityEntityList.get(position);
    }
}
