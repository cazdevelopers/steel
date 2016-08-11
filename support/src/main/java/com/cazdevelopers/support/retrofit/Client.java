package com.cazdevelopers.support.retrofit;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public abstract class Client {
    public abstract String getUrl();
    public Converter.Factory getConverter() {
        return GsonConverterFactory.create();
    }
    public abstract Class getApi();
}
