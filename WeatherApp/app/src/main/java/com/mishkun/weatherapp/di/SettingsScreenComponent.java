package com.mishkun.weatherapp.di;

import com.mishkun.weatherapp.presentation.settings.SettingsFragment;

import dagger.Subcomponent;

@Subcomponent
@SettingsScreen
public interface SettingsScreenComponent {
    void inject(SettingsFragment settingsFragment);
}
