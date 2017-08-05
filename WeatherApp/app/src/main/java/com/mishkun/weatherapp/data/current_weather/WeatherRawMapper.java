package com.mishkun.weatherapp.data.current_weather;

import com.mishkun.weatherapp.domain.entities.Temperature;
import com.mishkun.weatherapp.domain.entities.Weather;
import com.mishkun.weatherapp.domain.entities.WeatherConditions;

class WeatherRawMapper {
    static Weather toDomain(WeatherRaw weatherRaw, long timestamp) {
        return new Weather(new Temperature(weatherRaw.main.temperature), weatherRaw.main.humidity,
                hPaToMmHg(weatherRaw.main.pressureHPa),
                codeToCondition(weatherRaw.weather.get(0).weather_id), weatherRaw.wind.windSpeed,
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
