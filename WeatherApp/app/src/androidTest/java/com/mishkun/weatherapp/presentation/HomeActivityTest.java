package com.mishkun.weatherapp.presentation;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mishkun.weatherapp.PauseTimeUtil.freezeTimeMethod;
import static org.hamcrest.Matchers.is;

/*
 * Created by DV on Space 5 
 * 28.07.2017
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest{

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void clickWeatherFragment(){
        onView(withId(R.id.weatherIcon)).perform(click());
        onView(withId(R.id.weatherFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void clickCityFragment(){
        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.favouriteFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void clickSettingsFragment(){
        onView(withId(R.id.settingsIcon)).perform(click());
        onView(withId(R.id.settingsFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void check_Navigation(){
        onView(withId(R.id.weatherIcon)).perform(click());
        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.settingsIcon)).perform(click());

        onView(withId(R.id.settingsIcon)).perform(pressBack());
        onView(withId(R.id.favouriteFragment)).check(matches(is(isDisplayed())));
        onView(withId(R.id.favouriteFragment)).perform(pressBack());
        onView(withId(R.id.weatherFragment)).check(matches(is(isDisplayed())));
    }

}