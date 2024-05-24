/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.eu.fangkehou.weather.model.api.amap.Regeocode;

/**
 * Auto-generated: 2024-05-21 20:1:20
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AmapLocationResult {

    private String status;
    private Regeocode regeocode;
    private String info;
    private String infocode;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setRegeocode(Regeocode regeocode) {
         this.regeocode = regeocode;
     }
     public Regeocode getRegeocode() {
         return regeocode;
     }

    public void setInfo(String info) {
         this.info = info;
     }
     public String getInfo() {
         return info;
     }

    public void setInfocode(String infocode) {
         this.infocode = infocode;
     }
     public String getInfocode() {
         return infocode;
     }

     public String getCity() {
        String city = regeocode.getAddressComponent().getCity();
        return city == null ? regeocode.getAddressComponent().getProvince(): city;
     }

     public static AmapLocationResult parseInstance(String json) {
         Gson gson = new GsonBuilder()
                 .registerTypeAdapter(String.class, (JsonDeserializer<String>) (jsonElement, type, jsonDeserializationContext) -> {
                     try {
                         return jsonElement.getAsString();
                     }
                     catch (Exception e) {
                         return null;
                     }

                 })
                 .create();
         return gson.fromJson(json, AmapLocationResult.class);
     }

}