package org.eu.fangkehou.weather.service.api;

import java.io.IOException;

public abstract class ApiCallback<T> {

    public abstract void onFailure(IOException e);

    public abstract void onResponse(T result);
}
