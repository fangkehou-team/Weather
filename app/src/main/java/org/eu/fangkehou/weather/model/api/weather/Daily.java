/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api.weather;
import java.util.List;

/**
 * Auto-generated: 2024-05-21 21:54:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Daily {

    private List<Long> time;
    private List<Integer> weathercode;
    private List<Integer> precipitation_probability_max;
    private List<Double> temperature_2m_min;
    private List<Double> temperature_2m_max;
    private List<Long> sunrise;
    private List<Long> sunset;
    public void setTime(List<Long> time) {
         this.time = time;
     }
     public List<Long> getTime() {
         return time;
     }

    public void setWeathercode(List<Integer> weathercode) {
         this.weathercode = weathercode;
     }
     public List<Integer> getWeathercode() {
         return weathercode;
     }

    public void setPrecipitation_probability_max(List<Integer> precipitation_probability_max) {
         this.precipitation_probability_max = precipitation_probability_max;
     }
     public List<Integer> getPrecipitation_probability_max() {
         return precipitation_probability_max;
     }

    public void setTemperature_2m_min(List<Double> temperature_2m_min) {
         this.temperature_2m_min = temperature_2m_min;
     }
     public List<Double> getTemperature_2m_min() {
         return temperature_2m_min;
     }

    public void setTemperature_2m_max(List<Double> temperature_2m_max) {
         this.temperature_2m_max = temperature_2m_max;
     }
     public List<Double> getTemperature_2m_max() {
         return temperature_2m_max;
     }

    public void setSunrise(List<Long> sunrise) {
         this.sunrise = sunrise;
     }
     public List<Long> getSunrise() {
         return sunrise;
     }

    public void setSunset(List<Long> sunset) {
         this.sunset = sunset;
     }
     public List<Long> getSunset() {
         return sunset;
     }

}