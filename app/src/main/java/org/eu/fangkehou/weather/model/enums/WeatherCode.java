package org.eu.fangkehou.weather.model.enums;

import org.eu.fangkehou.weather.R;

import java.util.HashMap;
import java.util.Map;

public enum WeatherCode {
    CLEARSKY(0, "晴", R.drawable.day, R.drawable.night),
    MAINLYCLEAR(1, "晴", R.drawable.day, R.drawable.night),
    PARTLYCLOUDY(2, "多云", R.drawable.cloudy_day, R.drawable.cloudy_night),
    OVERCAST(3, "阴", R.drawable.cloudy, R.drawable.cloudy),
    FOG(45, "雾", R.drawable.foggy, R.drawable.foggy),
    FREEZINGFOG(48, "冻雾", R.drawable.foggy, R.drawable.foggy),
    LIGHTDRIZZLE(51, "细雨", R.drawable.rainy_light, R.drawable.rainy_light),
    MODERATEDRIZZLE(53, "细雨", R.drawable.rainy_moderate, R.drawable.rainy_moderate),
    DENSEDRIZZLE(55, "细雨", R.drawable.rainy_heavy, R.drawable.rainy_heavy),
    LIGHTFREEZINGDRIZZLE(56, "细雨", R.drawable.rainy_light, R.drawable.rainy_light),
    DENSEFREEZINGDRIZZLE(57, "细雨", R.drawable.rainy_freezing, R.drawable.rainy_freezing),
    LIGHTRAIN(61, "小雨", R.drawable.rainy_light, R.drawable.rainy_light),
    MODERATERAIN(63, "中雨", R.drawable.rainy_moderate, R.drawable.rainy_moderate),
    HEAVYRAIN(65, "大雨", R.drawable.rainy_heavy, R.drawable.rainy_heavy),
    LIGHTFREEZINGRAIN(66, "冻雨", R.drawable.rainy_light, R.drawable.rainy_light),
    HEAVYFREEZINGRAIN(67, "冻雨", R.drawable.rainy_freezing, R.drawable.rainy_freezing),
    LIGHTSNOWFALL(71, "小雪", R.drawable.snowy_light, R.drawable.snowy_light),
    MODERATESNOWFALL(73, "中雪", R.drawable.snowy_moderate, R.drawable.snowy_moderate),
    HEAVYSNOWFALL(75, "大雪", R.drawable.snowy_heavy, R.drawable.snowy_heavy),
    SNOWGRAINS(77, "冰雹", R.drawable.snowy_light, R.drawable.snowy_light),
    LIGHTRAINSHOWERS(80, "小雨", R.drawable.rainy_light, R.drawable.rainy_light),
    MODERATERAINSHOWERS(81, "中雨", R.drawable.rainy_moderate, R.drawable.rainy_moderate),
    VIOLENTRAINSHOWERS(82, "大雨", R.drawable.rainy_heavy, R.drawable.rainy_heavy),
    LIGHTSNOWSHOWERS(85, "中雪", R.drawable.snowy_moderate, R.drawable.snowy_moderate),
    HEAVYSNOWSHOWERS(86, "大雪", R.drawable.snowy_heavy, R.drawable.snowy_heavy),
    LIGHTORMODERATETHUNDERSTORM(95, "LIGHT OR MODERATE THUNDERSTORM", R.drawable.thunder, R.drawable.thunder),
    THUNDERSTORMWITHSLIGHTHAIL(96, "THUNDERSTORM WITH SLIGHT HAIL", R.drawable.thunder, R.drawable.thunder),
    THUNDERSTORMWITHHEAVYHAIL(99, "THUNDERSTORM WITH HEAVY HAIL", R.drawable.thunder, R.drawable.thunder);

    private final Integer weatherCode;
    private final String weatherText;
    private final Integer weatherIconDay;
    private final Integer weatherIconNight;

    WeatherCode(Integer weatherCode, String weatherText, Integer weatherIconDay, Integer weatherIconNight) {
        this.weatherCode = weatherCode;
        this.weatherText = weatherText;
        this.weatherIconDay = weatherIconDay;
        this.weatherIconNight = weatherIconNight;
    }

    public Integer getWeatherCode() {
        return weatherCode;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public Integer getWeatherIconDay() {
        return weatherIconDay;
    }

    public Integer getWeatherIconNight() {
        return weatherIconNight;
    }

    private static Map<Integer,WeatherCode> codeWeatherCode = new HashMap<>();
    static {
        for (WeatherCode value : WeatherCode.values()) {
            codeWeatherCode.put(value.getWeatherCode(),value);
        }
    }

    public static WeatherCode find(int weatherCode){
        return codeWeatherCode.get(weatherCode);
    }
}