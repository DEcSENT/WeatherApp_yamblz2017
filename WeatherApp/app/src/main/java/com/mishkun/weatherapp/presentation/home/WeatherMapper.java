package com.mishkun.weatherapp.presentation.home;

import android.content.Context;
import android.content.res.Resources;

import com.mishkun.weatherapp.R;
import com.mishkun.weatherapp.di.WeatherScreen;
import com.mishkun.weatherapp.domain.entities.Weather;

import javax.inject.Inject;

@WeatherScreen
public class WeatherMapper {
    private final String pressureFmt;
    private final String windFmt;
    private final String humidityFmt;
    private final String temperatureFmt;

    @Inject
    WeatherMapper(Context context) {
        Resources resources = context.getResources();
        this.pressureFmt = resources.getString(R.string.pressure_fmt_string);
        this.windFmt = resources.getString(R.string.wind_fmt_string);
        this.humidityFmt = resources.getString(R.string.humidity_fmt_string);
        this.temperatureFmt = resources.getString(R.string.temperature_fmt_string);
    }

    WeatherViewModel toWeatherViewModel(Weather weather) {
        int res;
        switch (weather.getWeatherConditions()) {
            case RAIN:
                res = R.drawable.rain;
                break;
            case CLOUDY:
                res = R.drawable.cloudy;
                break;
            case PARTLY_CLOUDY:
                res = R.drawable.partly_cloudy;
                break;
            case THUNDERSTORM:
                res = R.drawable.thunderstorm;
                break;
            case FOG:
                res = R.drawable.fog;
                break;
            case CLEAR:
                res = R.drawable.sun;
                break;
            case DRIZZLE:
                res = R.drawable.drizzle;
                break;
            case SNOW:
                res = R.drawable.snowflake;
                break;
            default:
                res = R.drawable.sun;
        }
        return new WeatherViewModel(String.format(humidityFmt, weather.getHumidity()),
                String.format(pressureFmt, weather.getPressureMmHg()),
                String.format(temperatureFmt, weather.getTemperature().getCelsiusDegrees()),
                String.format(windFmt, weather.getWindSpeed()),
                res, weather.getCityName());
    }
}
