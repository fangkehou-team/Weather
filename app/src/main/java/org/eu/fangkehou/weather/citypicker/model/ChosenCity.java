package org.eu.fangkehou.weather.citypicker.model;

public class ChosenCity extends City {

    public ChosenCity(String name, String province, String code) {
        super(name, province, "已选城市", code);
    }
}
