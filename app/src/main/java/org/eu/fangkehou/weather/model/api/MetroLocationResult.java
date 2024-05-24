/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api;
import com.google.gson.Gson;
import org.eu.fangkehou.weather.model.api.metro.MetroLocation;

import java.util.List;

/**
 * Auto-generated: 2024-05-21 20:51:49
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class MetroLocationResult {

    private List<MetroLocation> results;
    private double generationtime_ms;

    public void setResults(List<MetroLocation> results) {
         this.results = results;
     }
     public List<MetroLocation> getResults() {
         return results;
     }

    public void setGenerationtime_ms(double generationtime_ms) {
         this.generationtime_ms = generationtime_ms;
     }
     public double getGenerationtime_ms() {
         return generationtime_ms;
     }

    public static MetroLocationResult parseInstance(String json) {
        Gson gson = new Gson();

        return gson.fromJson(json, MetroLocationResult.class);
    }
}