package org.eu.fangkehou.weather.citypicker.adapter;

import org.eu.fangkehou.weather.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
