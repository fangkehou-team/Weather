package org.eu.fangkehou.weather.model.view;

import org.eu.fangkehou.weather.model.api.weather.Daily;
import org.eu.fangkehou.weather.model.bean.WeatherData;
import org.eu.fangkehou.weather.model.bean.weather.DailyData;
import org.eu.fangkehou.weather.model.view.wether.DailyViewModel;
import org.eu.fangkehou.weather.model.view.wether.HourlyViewModel;
import org.eu.fangkehou.weather.model.view.wether.CurrentViewModel;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class WeatherViewModel implements Serializable {
    private CurrentViewModel currentData;
    private List<DailyViewModel> dailyData;
    private List<HourlyViewModel> hourlyData;

    public static WeatherViewModel fromWeatherData(WeatherData weatherData) {
        return WeatherViewModel.builder()
                .currentData(CurrentViewModel.fromCurrentData(weatherData.getCurrentData()))
                .dailyData(DailyViewModel.fromDailyData(weatherData.getDailyData()))
                .hourlyData(HourlyViewModel.fromHourlyData(weatherData.getHourlyData()))
                .build();
    }
}
