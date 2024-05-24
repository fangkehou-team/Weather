/**
  * Copyright 2024 bejson.com 
  */
package org.eu.fangkehou.weather.model.api.amap;

/**
 * Auto-generated: 2024-05-21 20:1:20
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Regeocode {

    private AddressComponent addressComponent;
    private String formatted_address;
    public void setAddressComponent(AddressComponent addressComponent) {
         this.addressComponent = addressComponent;
     }
     public AddressComponent getAddressComponent() {
         return addressComponent;
     }

    public void setFormatted_address(String formatted_address) {
         this.formatted_address = formatted_address;
     }
     public String getFormatted_address() {
         return formatted_address;
     }

}