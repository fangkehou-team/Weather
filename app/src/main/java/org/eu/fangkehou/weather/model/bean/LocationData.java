package org.eu.fangkehou.weather.model.bean;

import org.eu.fangkehou.weather.model.api.MetroLocationResult;
import org.eu.fangkehou.weather.model.api.metro.MetroLocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationData {

    /**
     * 城市id（在SQLite中的id，0永远为定位位置）
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    public static LocationData fromMetroLocation(MetroLocationResult metroLocationResult) {
        if (metroLocationResult.getResults().isEmpty()) {
            return null;
        }

        MetroLocation metroLocation = metroLocationResult.getResults().get(0);

        LocationData result = new LocationData();

        result.setLatitude(String.valueOf(metroLocation.getLatitude()));
        result.setLongitude(String.valueOf(metroLocation.getLongitude()));
        result.setName(metroLocation.getName());

        return result;
    }
}
