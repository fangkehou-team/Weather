/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api.weather;

/**
 * Auto-generated: 2024-05-21 21:54:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DailyUnits {

    private String time;
    private String weathercode;
    private String precipitation_probability_max;
    private String temperature_2m_min;
    private String temperature_2m_max;
    private String sunrise;
    private String sunset;
    public void setTime(String time) {
         this.time = time;
     }
     public String getTime() {
         return time;
     }

    public void setWeathercode(String weathercode) {
         this.weathercode = weathercode;
     }
     public String getWeathercode() {
         return weathercode;
     }

    public void setPrecipitation_probability_max(String precipitation_probability_max) {
         this.precipitation_probability_max = precipitation_probability_max;
     }
     public String getPrecipitation_probability_max() {
         return precipitation_probability_max;
     }

    public void setTemperature_2m_min(String temperature_2m_min) {
         this.temperature_2m_min = temperature_2m_min;
     }
     public String getTemperature_2m_min() {
         return temperature_2m_min;
     }

    public void setTemperature_2m_max(String temperature_2m_max) {
         this.temperature_2m_max = temperature_2m_max;
     }
     public String getTemperature_2m_max() {
         return temperature_2m_max;
     }

    public void setSunrise(String sunrise) {
         this.sunrise = sunrise;
     }
     public String getSunrise() {
         return sunrise;
     }

    public void setSunset(String sunset) {
         this.sunset = sunset;
     }
     public String getSunset() {
         return sunset;
     }

}