package com.mishkun.weatherapp.presentation;

import android.support.test.rule.ActivityTestRule;

import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static com.mishkun.weatherapp.PauseTimeUtil.freezeTimeMethod;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/*
 * Created by DV on Space 5 
 * 30.07.2017
 */
public class SettingsFragmentTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void openSettingsAndCheckSpinnerItem(){
        onView(withId(R.id.settingsIcon)).perform(click());

        onView(withId(R.id.refresh_weather_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("30 минут"))).perform(click());
        onView(withId(R.id.refresh_weather_spinner)).check(matches(withSpinnerText(containsString("30 минут"))));
        freezeTimeMethod();
    }

    @Test
    public void openAndClose_SettingsAndCheckSpinnerItem(){
        onView(withId(R.id.settingsIcon)).perform(click());

        onView(withId(R.id.refresh_weather_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("30 минут"))).perform(click());
        onView(withId(R.id.refresh_weather_spinner)).check(matches(withSpinnerText(containsString("30 минут"))));
        freezeTimeMethod();

        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.settingsIcon)).perform(click());
        onView(withId(R.id.refresh_weather_spinner)).check(matches(withSpinnerText(containsString("30 минут"))));
    }

    @Test
    public void openSettingsAndAboutItem(){
        onView(withId(R.id.settingsIcon)).perform(click());
        onView(withId(R.id.aboutButton)).perform(click());
        onView(withId(R.id.aboutFragment)).check(matches(is(isDisplayed())));

        freezeTimeMethod();
    }
}