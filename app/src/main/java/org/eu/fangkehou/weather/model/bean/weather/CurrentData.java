package org.eu.fangkehou.weather.model.bean.weather;

import org.eu.fangkehou.weather.model.api.weather.CurrentWeather;

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
public class CurrentData {
    private Long updateTime;
    private Double temperature;
    private Double windSpeed;
    private Integer windDirection;
    private Integer isDay;
    private Integer weatherCode;

    public static CurrentData fromCurrentWeather(CurrentWeather currentWeather) {
        if (currentWeather == null) {
            return null;
        }

        return CurrentData.builder()
                .updateTime(currentWeather.getTime())
                .temperature(currentWeather.getTemperature())
                .windSpeed(currentWeather.getWindspeed())
                .windDirection(currentWeather.getWinddirection())
                .isDay(currentWeather.getIs_day())
                .weatherCode(currentWeather.getWeathercode())
                .build();
    }
}
