package com.mishkun.weatherapp.presentation;
/*
 * Created by DV on Space 5 
 * 11.08.2017
 */

import android.support.test.espresso.contrib.RecyclerViewActions;
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

@RunWith(AndroidJUnit4.class)
public class FavouriteFragmentTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void checkFavouriteFragment(){
        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.favouriteFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
    }

    @Test
    public void checkSuggestFragment(){
        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.favouriteFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
        onView(withId(R.id.suggestButton)).perform(click());
        onView(withId(R.id.suggestFragment)).check(matches(is(isDisplayed())));
    }

    @Test
    public void checkFirstDefaultItem_InSuggestFragment(){
        onView(withId(R.id.favouriteIcon)).perform(click());
        onView(withId(R.id.favouriteFragment)).check(matches(is(isDisplayed())));
        freezeTimeMethod();
        onView(withId(R.id.suggestButton)).perform(click());
        onView(withId(R.id.suggestFragment)).check(matches(is(isDisplayed())));
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
