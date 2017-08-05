package com.mishkun.weatherapp.presentation;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mishkun.weatherapp.PauseTimeUtil.freezeTimeMethod;

/*
 * Created by DV on Space 5 
 * 28.07.2017
 */
public class SuggestFragmentTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void checkTextView(){
        onView(ViewMatchers.withId(R.id.cityTextView)).perform(click());
        freezeTimeMethod();
        onView(withId(R.id.citySuggestEditText))
                .perform(ViewActions.typeText("Moscow"))
                .check(matches(withText("Moscow")));
        freezeTimeMethod();

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        freezeTimeMethod();
    }
}