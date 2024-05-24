package org.eu.fangkehou.weather.bean;

import java.util.List;

public class City {
    private String id;
    private String name;
    private List<City> cityList;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
    public List<City> getCityList() {
        return cityList;
    }
}
