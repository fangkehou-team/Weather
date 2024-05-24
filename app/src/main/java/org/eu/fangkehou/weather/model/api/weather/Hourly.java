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
public class Hourly {

    private List<Long> time;
    private List<Double> temperature_2m;
    private List<Integer> weathercode;
    private List<Integer> precipitation_probability;
    private List<Integer> winddirection_10m;
    private List<Double> apparent_temperature;
    private List<Integer> relativehumidity_2m;
    private List<Integer> cloudcover;
    private List<Double> surface_pressure;
    private List<Integer> is_day;
    private List<Double> uv_index;
    public void setTime(List<Long> time) {
         this.time = time;
     }
     public List<Long> getTime() {
         return time;
     }

    public void setTemperature_2m(List<Double> temperature_2m) {
         this.temperature_2m = temperature_2m;
     }
     public List<Double> getTemperature_2m() {
         return temperature_2m;
     }

    public void setWeathercode(List<Integer> weathercode) {
         this.weathercode = weathercode;
     }
     public List<Integer> getWeathercode() {
         return weathercode;
     }

    public void setPrecipitation_probability(List<Integer> precipitation_probability) {
         this.precipitation_probability = precipitation_probability;
     }
     public List<Integer> getPrecipitation_probability() {
         return precipitation_probability;
     }

    public void setWinddirection_10m(List<Integer> winddirection_10m) {
         this.winddirection_10m = winddirection_10m;
     }
     public List<Integer> getWinddirection_10m() {
         return winddirection_10m;
     }

    public void setApparent_temperature(List<Double> apparent_temperature) {
         this.apparent_temperature = apparent_temperature;
     }
     public List<Double> getApparent_temperature() {
         return apparent_temperature;
     }

    public void setRelativehumidity_2m(List<Integer> relativehumidity_2m) {
         this.relativehumidity_2m = relativehumidity_2m;
     }
     public List<Integer> getRelativehumidity_2m() {
         return relativehumidity_2m;
     }

    public void setCloudcover(List<Integer> cloudcover) {
         this.cloudcover = cloudcover;
     }
     public List<Integer> getCloudcover() {
         return cloudcover;
     }

    public void setSurface_pressure(List<Double> surface_pressure) {
         this.surface_pressure = surface_pressure;
     }
     public List<Double> getSurface_pressure() {
         return surface_pressure;
     }

    public void setIs_day(List<Integer> is_day) {
         this.is_day = is_day;
     }
     public List<Integer> getIs_day() {
         return is_day;
     }

    public void setUv_index(List<Double> uv_index) {
         this.uv_index = uv_index;
     }
     public List<Double> getUv_index() {
         return uv_index;
     }

}