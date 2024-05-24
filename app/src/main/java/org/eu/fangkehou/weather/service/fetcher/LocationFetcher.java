package org.eu.fangkehou.weather.service.fetcher;

import android.content.Context;

import org.eu.fangkehou.weather.model.api.AmapLocationResult;
import org.eu.fangkehou.weather.model.api.MetroLocationResult;
import org.eu.fangkehou.weather.model.bean.LocationData;
import org.eu.fangkehou.weather.service.api.ApiCallback;
import org.eu.fangkehou.weather.service.api.LocationApi;
import org.eu.fangkehou.weather.service.data.LocationDatabase;

import java.io.IOException;

public class LocationFetcher {

    public static void fetchAndCacheLocationData(Context context, int id, String latitude, String longitude, ApiCallback<LocationData> callback) {
        LocationApi.getAmapLocationResultWithLatAndLong(latitude, longitude, new ApiCallback<AmapLocationResult>() {
            @Override
            public void onFailure(IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(AmapLocationResult result) {
                String city = result.getCity();
                fetchAndCacheLocationData(context, id, city, callback);
            }
        });
    }

    public static void fetchAndCacheLocationData(Context context, int id, String city, ApiCallback<LocationData> callback) {
        LocationApi.getMetroLocationResultWithCity(city, new ApiCallback<MetroLocationResult>() {
            @Override
            public void onFailure(IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(MetroLocationResult result) {
                LocationData locationData = LocationData.fromMetroLocation(result);
                if (locationData == null) {
                    callback.onFailure(new IOException("Can not get LocationData Instance"));
                    return;
                }

                if (id != -1) {
                    locationData.setId(id);
                }

                int resultCode = LocationDatabase.getInstance(context).saveLocation(locationData);
                if (resultCode != -1) {
                    callback.onResponse(locationData);
                } else {
                    callback.onFailure(new IOException("Can not save to database"));
                }

            }
        });
    }

    public static LocationData getCachedLocationDataAsync(Context context, int id) {
        return LocationDatabase.getInstance(context).getLocation(id);
    }

}
