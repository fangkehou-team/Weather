package org.eu.fangkehou.weather.service.api;

import android.util.Log;

import okhttp3.*;
import org.eu.fangkehou.weather.model.api.AmapLocationResult;
import org.eu.fangkehou.weather.model.api.MetroLocationResult;
import org.eu.fangkehou.weather.util.DemoData;

import java.io.IOException;

public class LocationApi {

    //Amap Location
    private static void getAmapLocationResultWithLatAndLongImpl(String latitude, String longitude,ApiCallback<AmapLocationResult> callback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("lbs.amap.com")
                .addPathSegments("AMapService/v3/geocode/regeo")
                .query("key=a4abc6f3f9cb2db7d77675e523e8b416&s=rsv3&language=zh_cn&radius=1000&output=json&" +
                        "logversion=2.0&appname=https%3A%2F%2Flbs.amap.com%2Ffn%2Fiframe%2F&csid=CEC8FA99-83A7-445C-BCAD-80C9BE69A963" +
                        "&sdkversion=1.4.26")
                .addQueryParameter("location",  longitude + "," + latitude)
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("referer", "https://lbs.amap.com/fn/iframe/?id=383&guide=1&litebar=4")
                .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
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

//                Log.i("LocationApi", response.body().string());
                callback.onResponse(AmapLocationResult.parseInstance(response.body().string()));
            }
        });
    }

    public static void getAmapLocationResultWithLatAndLong(String latitude, String longitude, ApiCallback<AmapLocationResult> callback) {
        new Thread() {
            @Override
            public void run() {
                callback.onResponse(AmapLocationResult.parseInstance(DemoData.AmapLocationData));
            }
        }.start();
//        getAmapLocationResultWithLatAndLongImpl(latitude, longitude, callback);
    }

    //Metro Location
    private static void getMetroLocationResultWithCityImpl(String city, ApiCallback<MetroLocationResult> callback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("geocoding-api.open-meteo.com")
                .addPathSegments("v1/search")
                .query("language=zh&count=1")
                .addQueryParameter("name", city)
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
                callback.onResponse(MetroLocationResult.parseInstance(response.body().string()));
            }
        });
    }

    public static void getMetroLocationResultWithCity(String city, ApiCallback<MetroLocationResult> callback) {
        new Thread() {
            @Override
            public void run() {

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ignored) {
//
//                }

                callback.onResponse(MetroLocationResult.parseInstance(DemoData.MetroLocationData));
            }
        }.start();

//        getMetroLocationResultWithCityImpl(city, callback);
    }
}
