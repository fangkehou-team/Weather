package org.eu.fangkehou.weather.model.bean;

import org.eu.fangkehou.weather.model.api.MetroWeatherResult;
import org.eu.fangkehou.weather.model.api.weather.Daily;
import org.eu.fangkehou.weather.model.bean.weather.CurrentData;
import org.eu.fangkehou.weather.model.bean.weather.DailyData;
import org.eu.fangkehou.weather.model.bean.weather.HourlyData;

import java.util.List;

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
public class WeatherData {
    private CurrentData currentData;
    private List<DailyData> dailyData;
    private List<HourlyData> hourlyData;

    public void updateWeather(WeatherData newWeatherData) {
        this.setCurrentData(newWeatherData.getCurrentData());
        this.setDailyData(newWeatherData.getDailyData());
        this.setHourlyData(newWeatherData.getHourlyData());
    }

    public static WeatherData fromMetroWeather(MetroWeatherResult metroWeatherResult) {
        return WeatherData.builder()
                .currentData(CurrentData.fromCurrentWeather(metroWeatherResult.getCurrent_weather()))
                .dailyData(DailyData.fromDaily(metroWeatherResult.getDaily()))
                .hourlyData(HourlyData.fromHourly(metroWeatherResult.getHourly()))
                .build();
    }
}
