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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.cityTextView);
        }
    }

    public SuggestRecyclerAdapter(List<Prediction> list, SuggestFragment.onClickRecyclerItem onClickRecyclerItem) {
        this.predictionList = list;
        this.onClickRecyclerItem = onClickRecyclerItem;
    }

    public void setNewList(List<Prediction> list) {
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
        holder.itemView.setOnClickListener(
                view -> {
                    Log.v("ONCLICK", pred.getPlaceId());
        });
        holder.title.setText(pred.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRecyclerItem.onclick(predictionList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictionList.size();
    }
}

