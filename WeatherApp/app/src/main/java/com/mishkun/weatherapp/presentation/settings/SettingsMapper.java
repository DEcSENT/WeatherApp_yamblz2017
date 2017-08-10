package com.mishkun.weatherapp.presentation.settings;

import android.content.Context;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.SettingsScreen;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

@SettingsScreen
public class SettingsMapper {
    private final int[] refreshRateMapping;

    @Inject
    SettingsMapper(Context context) {
        refreshRateMapping = context.getResources().getIntArray(R.array.time_schedule_preference_entry_values);
    }

    int toViewSpinnerPosition(int minutes) {
        for (int i = 0; i < refreshRateMapping.length; i++) {
            if (refreshRateMapping[i] == minutes) {
                return i;
            }
        }
        return 0;
    }

    int toActualMinutes(int position) {
        return refreshRateMapping[position];
    }

    int millisToMinutes(long millis) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(millis);
    }

    long minutesToMillis(int minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }
}
