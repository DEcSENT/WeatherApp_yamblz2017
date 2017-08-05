package com.mishkun.weatherapp.presentation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mishkun.weatherapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends DialogFragment {

    public static final String TAG = AboutFragment.class.getSimpleName();
    private AnimationDrawable frameAnimation;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView parrot = (ImageView) view.findViewById(R.id.animated_parrot);
        frameAnimation = (AnimationDrawable) parrot.getBackground();
    }

    @Override
    public void onResume() {
        super.onResume();
        frameAnimation.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        frameAnimation.stop();
    }
}
