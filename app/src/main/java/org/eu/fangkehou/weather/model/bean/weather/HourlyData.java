package org.eu.fangkehou.weather.model.bean.weather;

import org.eu.fangkehou.weather.model.api.weather.Hourly;

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
public class HourlyData {
    private Long time;
    private Double temperature;
    private Integer weatherCode;

    //降水概率
    private Integer precipitationProbability;
    private Integer windDirection;

    //体感温度
    private Double apparentTemperature;

    //相对湿度
    private Integer relativeHumidity;

    //云量
    private Integer cloudCover;

    //地面气压
    private Double surfacePressure;

    private Integer isDay;

    //紫外线指数
    private Double uvIndex;

    public static List<HourlyData> fromHourly(Hourly hourly) {
        if (hourly == null) {
            return new ArrayList<>();
        }

        ArrayList<HourlyData> result = new ArrayList<>();

        int allCount = hourly.getTime().size();
        for (int i = 0; i < allCount; i++) {
            result.add(HourlyData.builder()
                    .time(hourly.getTime().get(i))
                    .temperature(hourly.getTemperature_2m().get(i))
                    .weatherCode(hourly.getWeathercode().get(i))
                    .precipitationProbability(hourly.getPrecipitation_probability().get(i))
                    .windDirection(hourly.getWinddirection_10m().get(i))
                    .apparentTemperature(hourly.getApparent_temperature().get(i))
                    .relativeHumidity(hourly.getRelativehumidity_2m().get(i))
                    .cloudCover(hourly.getCloudcover().get(i))
                    .surfacePressure(hourly.getSurface_pressure().get(i))
                    .isDay(hourly.getIs_day().get(i))
                    .uvIndex(hourly.getUv_index().get(i))
                    .build());
        }

        return result;
    }
}
