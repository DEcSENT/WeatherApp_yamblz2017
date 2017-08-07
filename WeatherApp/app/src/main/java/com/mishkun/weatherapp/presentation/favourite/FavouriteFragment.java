package com.mishkun.weatherapp.presentation.favourite;
/*
 * Created by DV on Space 5 
 * 03.08.2017
 */

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;
import com.mishkun.weatherapp.db.CityEntity;
import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.presentation.suggest.SuggestFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment {

    @Inject
    public FavouritePresenter favouritePresenter;

    private FavouriteRecyclerAdapter favouriteRecyclerAdapter;

    @BindView(R.id.favouriteRecyclerView) RecyclerView favouriteRecyclerView;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.favourite_title);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        ((HasComponent<AppComponent>) getActivity().getApplication()).getComponent().inject(this);

        List<CityEntity> list = new ArrayList<>();
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityName("jopa");
        cityEntity.setFavourite("1");
        list.add(cityEntity);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                if (!favouriteRecyclerAdapter.getCity(position).getFavourite().equals("1")){
                    favouritePresenter.deleteCity(favouriteRecyclerAdapter.getCity(position).getUid());
                }
                favouriteRecyclerAdapter.notifyItemChanged(position);
            }
        };

        favouriteRecyclerAdapter = new FavouriteRecyclerAdapter(list, new onClickRecyclerItem() {
            @Override
            public void onclick(CityEntity city) {
                Log.v("ONCLICK FAV ITEM", city.getCityName());
                if(!city.getFavourite().equals("1")) {
                    favouritePresenter.setFavouriteCity(city.getCityName(), city.getLatitude(), city.getFavourite());
                }
            }
        });

        favouriteRecyclerView.setAdapter(favouriteRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        favouriteRecyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(favouriteRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        favouritePresenter.onAttach(this);
        favouritePresenter.getCitiesList();
    }

    @Override
    public void onPause() {
        super.onPause();
        favouritePresenter.onDetach();
    }

    public void setFavouriteCities(List<CityEntity> cities){
        favouriteRecyclerAdapter.setNewList(cities);
        favouriteRecyclerView.setAdapter(favouriteRecyclerAdapter);
    }

    public interface onClickRecyclerItem {
        void onclick(CityEntity city);
    }

    public void showMessage(String string){
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
    }
}
