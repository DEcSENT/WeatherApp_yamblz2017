package com.mishkun.weatherapp.presentation.suggest;
/*
 * Created by DV on Space 5 
 * 24.07.2017
 */
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.AppComponent;
import com.mishkun.weatherapp.di.HasComponent;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class SuggestFragment extends Fragment implements SuggestView{
    public static final String TAG = SuggestFragment.class.getSimpleName();
    private static final int TIME_DELAY = 1000;

    @BindView(R.id.citySuggestEditText) EditText citySuggestEditText;
    @BindView(R.id.recyclerView) RecyclerView suggestRecyclerView;

    @Inject
    public SuggestPresenter suggestPresenter;

    private SuggestRecyclerAdapter suggestRecyclerAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((HasComponent<AppComponent>) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggest,
                container, false);
        ButterKnife.bind(this, view);

        setRxTextChangerListener();

        List<Prediction> list = new ArrayList<>();
        list.add(new Prediction("Москва", "ChIJybDUc_xKtUYRTM9XV8zWRD0", "ChIJybDUc_xKtUYRTM9XV8zWRD0"));
        suggestRecyclerAdapter = new SuggestRecyclerAdapter(list, new onClickRecyclerItem() {
            @Override
            public void onclick(Prediction prediction) {
                Log.v("From recycler click ", prediction.getDescription());
                Log.v("From recycler city ID", prediction.getPlaceId());
                suggestPresenter.getCityCoordinatesFromWeb(prediction.getPlaceId());
            }
        });
        suggestRecyclerView.setAdapter(suggestRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        suggestRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        suggestPresenter.onAttach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        suggestPresenter.onDetach();
    }

    public void setRxTextChangerListener(){
        Observable<String> cityObservable = RxTextView.textChanges(citySuggestEditText)
                .filter(charSequence -> charSequence.length() > 1)
                .debounce(TIME_DELAY, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString);
        cityObservable.subscribe(string -> suggestPresenter.getSuggestFromWeb(string));
    }

    @Override
    public void setSuggestAdapter(List<Prediction> list) {
        suggestRecyclerAdapter.setNewList(list);
        suggestRecyclerView.setAdapter(suggestRecyclerAdapter);
    }

    @Override
    public void showError(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
    }

    public interface onClickRecyclerItem {
        void onclick(Prediction prediction);
    }

    /* Close fragment and hide keyboard. */
    public void terminateFragment(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(citySuggestEditText.getWindowToken(), 0);
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SuggestFragment suggestFragment = (SuggestFragment) fragmentManager.findFragmentByTag("SuggestFragment");
        fragmentTransaction.remove(suggestFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}
