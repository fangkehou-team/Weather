/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api;

import com.google.gson.Gson;
import org.eu.fangkehou.weather.model.api.weather.*;

/**
 * Auto-generated: 2024-05-21 21:54:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class MetroWeatherResult {

    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private int elevation;
    private CurrentWeatherUnits current_weather_units;
    private CurrentWeather current_weather;
    private HourlyUnits hourly_units;
    private Hourly hourly;
    private DailyUnits daily_units;
    private Daily daily;
    public void setLatitude(double latitude) {
         this.latitude = latitude;
     }
     public double getLatitude() {
         return latitude;
     }

    public void setLongitude(double longitude) {
         this.longitude = longitude;
     }
     public double getLongitude() {
         return longitude;
     }

    public void setGenerationtime_ms(double generationtime_ms) {
         this.generationtime_ms = generationtime_ms;
     }
     public double getGenerationtime_ms() {
         return generationtime_ms;
     }

    public void setUtc_offset_seconds(int utc_offset_seconds) {
         this.utc_offset_seconds = utc_offset_seconds;
     }
     public int getUtc_offset_seconds() {
         return utc_offset_seconds;
     }

    public void setTimezone(String timezone) {
         this.timezone = timezone;
     }
     public String getTimezone() {
         return timezone;
     }

    public void setTimezone_abbreviation(String timezone_abbreviation) {
         this.timezone_abbreviation = timezone_abbreviation;
     }
     public String getTimezone_abbreviation() {
         return timezone_abbreviation;
     }

    public void setElevation(int elevation) {
         this.elevation = elevation;
     }
     public int getElevation() {
         return elevation;
     }

    public void setCurrent_weather_units(CurrentWeatherUnits current_weather_units) {
         this.current_weather_units = current_weather_units;
     }
     public CurrentWeatherUnits getCurrent_weather_units() {
         return current_weather_units;
     }

    public void setCurrent_weather(CurrentWeather current_weather) {
         this.current_weather = current_weather;
     }
     public CurrentWeather getCurrent_weather() {
         return current_weather;
     }

    public void setHourly_units(HourlyUnits hourly_units) {
         this.hourly_units = hourly_units;
     }
     public HourlyUnits getHourly_units() {
         return hourly_units;
     }

    public void setHourly(Hourly hourly) {
         this.hourly = hourly;
     }
     public Hourly getHourly() {
         return hourly;
     }

    public void setDaily_units(DailyUnits daily_units) {
         this.daily_units = daily_units;
     }
     public DailyUnits getDaily_units() {
         return daily_units;
     }

    public void setDaily(Daily daily) {
         this.daily = daily;
     }
     public Daily getDaily() {
         return daily;
     }

    public static MetroWeatherResult parseInstance(String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, MetroWeatherResult.class);
    }}