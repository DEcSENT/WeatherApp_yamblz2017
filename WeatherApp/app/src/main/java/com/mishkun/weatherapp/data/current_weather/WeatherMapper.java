package com.mishkun.weatherapp.data.current_weather;

import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;

class WeatherMapper {
    static com.mishkun.weatherapp.domain.entities.Weather toDomain(Weather weather, long timestamp) {
        return new com.mishkun.weatherapp.domain.entities.Weather(new Temperature(weather.main.temperature), weather.main.humidity,
                hPaToMmHg(weather.main.pressureHPa),
                codeToCondition(weather.weather.get(0).weather_id), weather.wind.windSpeed,
                timestamp, null);
    }

    // function to convert hectopascal to millimeter mercury. Formula from here: http://www.convertunits.com/from/mm+Hg/to/hPa
    private static double hPaToMmHg(double pressureHPa) {
        return pressureHPa * 0.750061561303;
    }

    private static WeatherConditions codeToCondition(int code) {
        if (code >= 200 && code < 300) {
            return WeatherConditions.THUNDERSTORM;
        } else if (code >= 300 && code < 400) {
            return WeatherConditions.DRIZZLE;
        } else if (code >= 500 && code < 600) {
            return WeatherConditions.RAIN;
        } else if (code >= 600 && code < 700) {
            return WeatherConditions.SNOW;
        } else if (code >= 700 && code < 800) {
            return WeatherConditions.FOG;
        } else if (code >= 800 && code < 803) {
            return WeatherConditions.PARTLY_CLOUDY;
        } else if (code >= 803 && code < 900) {
            return WeatherConditions.CLOUDY;
        } else {
            return WeatherConditions.CLEAR;
        }
    }
}
