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
public class CurrentWeather {

    private long time;
    private int interval;
    private double temperature;
    private double windspeed;
    private int winddirection;
    private int is_day;
    private int weathercode;
    public void setTime(long time) {
         this.time = time;
     }
     public long getTime() {
         return time;
     }

    public void setInterval(int interval) {
         this.interval = interval;
     }
     public int getInterval() {
         return interval;
     }

    public void setTemperature(double temperature) {
         this.temperature = temperature;
     }
     public double getTemperature() {
         return temperature;
     }

    public void setWindspeed(double windspeed) {
         this.windspeed = windspeed;
     }
     public double getWindspeed() {
         return windspeed;
     }

    public void setWinddirection(int winddirection) {
         this.winddirection = winddirection;
     }
     public int getWinddirection() {
         return winddirection;
     }

    public void setIs_day(int is_day) {
         this.is_day = is_day;
     }
     public int getIs_day() {
         return is_day;
     }

    public void setWeathercode(int weathercode) {
         this.weathercode = weathercode;
     }
     public int getWeathercode() {
         return weathercode;
     }

}