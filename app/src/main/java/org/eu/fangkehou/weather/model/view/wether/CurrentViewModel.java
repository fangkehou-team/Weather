package org.eu.fangkehou.weather.model.view.wether;

import org.eu.fangkehou.weather.model.bean.weather.CurrentData;
import org.eu.fangkehou.weather.model.enums.WeatherCode;
import org.eu.fangkehou.weather.model.enums.WindDirection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
public class CurrentViewModel implements Serializable {
    private LocalDateTime updateTime;
    private String temperature;
    private String windSpeed;
    private WindDirection windDirection;
    private Boolean isDay;
    private WeatherCode weatherCode;

    public static CurrentViewModel fromCurrentData(CurrentData currentData) {
        if (currentData == null) {
            return null;
        }
        return CurrentViewModel.builder()
                .updateTime(LocalDateTime.ofEpochSecond(currentData.getUpdateTime(), 0,ZoneOffset.of("+8")))
                .temperature(currentData.getTemperature() + "°")
                .windSpeed(currentData.getWindSpeed() + " km/h" )
                .windDirection(WindDirection.find(currentData.getWindDirection()))
                .isDay(currentData.getIsDay() == 1)
                .weatherCode(WeatherCode.find(currentData.getWeatherCode()))
                .build();
    }
}
