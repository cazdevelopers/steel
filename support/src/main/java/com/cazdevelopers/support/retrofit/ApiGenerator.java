package com.cazdevelopers.support.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by coreywoodfield on 8/10/16.
 */
public class ApiGenerator {

    public static <C extends Client> Object createApi(C client) {
        return new Retrofit.Builder()
                .baseUrl(client.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(client.getApi());
    }
}
