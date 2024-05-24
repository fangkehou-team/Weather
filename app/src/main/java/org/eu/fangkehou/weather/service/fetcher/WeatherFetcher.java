package org.eu.fangkehou.weather.service.fetcher;

import android.content.Context;

import org.eu.fangkehou.weather.model.api.MetroWeatherResult;
import org.eu.fangkehou.weather.model.bean.WeatherData;
import org.eu.fangkehou.weather.service.api.ApiCallback;
import org.eu.fangkehou.weather.service.api.WeatherApi;
import org.eu.fangkehou.weather.service.data.WeatherDatabase;

import java.io.IOException;

public class WeatherFetcher {

    public static void fetchAndCacheWeatherData(Context context, int id, String latitude, String longitude, ApiCallback<WeatherData> callback) {
        WeatherApi.getMetroLocationResultWithLatAndLong(latitude, longitude, new ApiCallback<MetroWeatherResult>() {
            @Override
            public void onFailure(IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(MetroWeatherResult result) {
                WeatherData weatherData = WeatherData.fromMetroWeather(result);

                WeatherDatabase.getInstance(context).saveWeather(id, weatherData);

                callback.onResponse(weatherData);
            }
        });
    }

    public static WeatherData getCachedLocationDataAsync(Context context, int id) {
        return WeatherDatabase.getInstance(context).getWeather(id);
    }
}
