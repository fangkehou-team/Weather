package org.eu.fangkehou.weather.service.api;

import okhttp3.*;
import org.eu.fangkehou.weather.model.api.MetroWeatherResult;
import org.eu.fangkehou.weather.util.DemoData;

import java.io.IOException;

public class WeatherApi {

    private static void getMetroLocationResultWithLatAndLongImpl(String latitude, String longitude, ApiCallback<MetroWeatherResult> callback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.open-meteo.com")
                .addPathSegments("v1/forecast")
                .query("current_weather=true&timezone=auto&timeformat=unixtime" +
                        "&hourly=temperature_2m,weathercode,precipitation_probability,weathercode,winddirection_10m,apparent_temperature,relativehumidity_2m,cloudcover,surface_pressure,is_day,uv_index" +
                        "&daily=weathercode,precipitation_probability_max,temperature_2m_min,temperature_2m_max,sunrise,sunset")
                .addQueryParameter("longitude", longitude)
                .addQueryParameter("latitude", latitude)
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                callback.onResponse(MetroWeatherResult.parseInstance(response.body().string()));
            }
        });
    }

    public static void getMetroLocationResultWithLatAndLong(String latitude, String longitude, ApiCallback<MetroWeatherResult> callback) {
        new Thread() {
            @Override
            public void run() {

//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException ignored) {
//
//                }

                callback.onResponse(MetroWeatherResult.parseInstance(DemoData.MetroWeatherData));
            }
        }.start();

//        getMetroLocationResultWithLatAndLongImpl(latitude, longitude, callback);
    }
}
