package org.eu.fangkehou.weather.model.view.wether;

import org.eu.fangkehou.weather.model.bean.weather.HourlyData;
import org.eu.fangkehou.weather.model.enums.WeatherCode;
import org.eu.fangkehou.weather.model.enums.WindDirection;

import java.time.Instant;
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
public class HourlyViewModel {
    private LocalDateTime time;
    private String temperature;
    private WeatherCode weatherCode;

    //降水概率
    private String precipitationProbability;
    private WindDirection windDirection;

    //体感温度
    private String apparentTemperature;

    //相对湿度
    private String relativeHumidity;

    //云量
    private String cloudCover;

    //地面气压
    private String surfacePressure;

    private Boolean isDay;

    //紫外线指数
    private Double uvIndex;

    public static List<HourlyViewModel> fromHourlyData(List<HourlyData> hourlyData) {
        if (hourlyData == null) {
            return new ArrayList<>();
        }

        ArrayList<HourlyViewModel> result = new ArrayList<>();

        for (HourlyData hourlyDataItem : hourlyData) {
            result.add(HourlyViewModel.builder()
                    .time(Instant.ofEpochSecond(hourlyDataItem.getTime()).atZone(ZoneOffset.of("+8")).toLocalDateTime())
                    .temperature(hourlyDataItem.getTemperature() + "°")
                    .weatherCode(WeatherCode.find(hourlyDataItem.getWeatherCode()))
                    .precipitationProbability(hourlyDataItem.getPrecipitationProbability() + "%")
                    .windDirection(WindDirection.find(hourlyDataItem.getWindDirection()))
                    .apparentTemperature(hourlyDataItem.getApparentTemperature() + "°")
                    .relativeHumidity(hourlyDataItem.getRelativeHumidity() + "%")
                    .cloudCover(hourlyDataItem.getCloudCover() + "%")
                    .surfacePressure(hourlyDataItem.getSurfacePressure() + " hPa")
                    .isDay(hourlyDataItem.getIsDay() == 1)
                    .uvIndex(hourlyDataItem.getUvIndex())
                    .build());
        }

        return result;
    }
}
