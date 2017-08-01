package com.mishkun.weatherapp.presentation;

import com.mishkun.weatherapp.common.Subscriptable;

public abstract class RxPresenter<V> extends Subscriptable {

    /*
    *   Presenter logic
    * */
    protected V view;

    public void attachView(V view) {
        this.view = view;
        onAttach();
    }

    protected abstract void onAttach();

    public void detachView() {
        this.view = null;
        onDetach();
        dispose();
    }

    protected abstract void onDetach();
}
