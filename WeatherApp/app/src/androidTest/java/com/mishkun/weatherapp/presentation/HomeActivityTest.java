package com.mishkun.weatherapp.presentation;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mishkun.weatherapp.HomeActivity;
import com.mishkun.weatherapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
    public void clickCityName(){
        onView(withId(R.id.weatherFragment)).check(matches(is(isDisplayed())));
        // This may produce NPE
        onView(ViewMatchers.withId(R.id.cityTextView)).perform(click());
    }

    @Test
    public void openAndCloseNavigationDrawer(){
        onView(withId(R.id.drawer_layout)).perform(ViewActions.swipeRight());
        freezeTimeMethod();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
    }

    @Test
    public void openWeatherFragment(){
        freezeTimeMethod();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home));
        onView(withId(R.id.weatherFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void openSettingsFragment(){
        freezeTimeMethod();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_settings));
        onView(withId(R.id.settingsFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void openAboutFragments(){
        freezeTimeMethod();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
        onView(withId(R.id.aboutFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }
}