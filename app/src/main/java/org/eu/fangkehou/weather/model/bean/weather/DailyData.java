package org.eu.fangkehou.weather.model.bean.weather;

import org.eu.fangkehou.weather.model.api.weather.Daily;

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
public class DailyData {
    private Long time;
    private Integer weatherCode;
    private Integer precipitationProbability;
    private Double temperatureMin;
    private Double temperatureMax;
    private Long sunrise;
    private Long sunset;

    public static List<DailyData> fromDaily(Daily daily) {
        if (daily == null) {
            return new ArrayList<>();
        }

        ArrayList<DailyData> result = new ArrayList<>();

        int allCount = daily.getTime().size();
        for (int i = 0; i < allCount; i++) {
            result.add(DailyData.builder()
                    .time(daily.getTime().get(i))
                    .weatherCode(daily.getWeathercode().get(i))
                    .precipitationProbability(daily.getPrecipitation_probability_max().get(i))
                    .temperatureMin(daily.getTemperature_2m_min().get(i))
                    .temperatureMax(daily.getTemperature_2m_max().get(i))
                    .sunrise(daily.getSunrise().get(i))
                    .sunset(daily.getSunset().get(i))
                    .build());
        }

        return result;
    }
}
