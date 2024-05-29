package org.eu.fangkehou.weather.model.view.wether;

import androidx.annotation.NonNull;

import org.eu.fangkehou.weather.model.bean.weather.DailyData;
import org.eu.fangkehou.weather.model.enums.WeatherCode;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
public class DailyViewModel implements Serializable {
    private LocalDate time;
    private WeatherCode weatherCode;
    private String precipitationProbability;
    private String temperatureMin;
    private String temperatureMax;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    public static List<DailyViewModel> fromDailyData(List<DailyData> dailyData) {
        if (dailyData == null) {
            return new ArrayList<>();
        }

        ArrayList<DailyViewModel> result = new ArrayList<>();

        for (DailyData dailyDataItem : dailyData) {
            result.add(DailyViewModel.builder()
                    .time(Instant.ofEpochSecond(dailyDataItem.getTime()).atZone(ZoneOffset.of("+8")).toLocalDate())
                    .weatherCode(WeatherCode.find(dailyDataItem.getWeatherCode()))
                    .precipitationProbability(dailyDataItem.getPrecipitationProbability() + "%")
                    .temperatureMin(dailyDataItem.getTemperatureMin() + "°")
                    .temperatureMax(dailyDataItem.getTemperatureMax() + "°")
                    .sunrise(Instant.ofEpochSecond(dailyDataItem.getSunrise()).atZone(ZoneOffset.of("+8")).toLocalDateTime())
                    .sunset(Instant.ofEpochSecond(dailyDataItem.getSunset()).atZone(ZoneOffset.of("+8")).toLocalDateTime())
                    .build());
        }

        return result;
    }
}
