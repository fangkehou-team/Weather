package org.eu.fangkehou.weather.citypicker.adapter;

import org.eu.fangkehou.weather.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
