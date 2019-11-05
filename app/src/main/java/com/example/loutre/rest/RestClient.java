package com.example.loutre.rest;

import com.example.loutre.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Create the Retrofit Object with BASE_URL
 *  Create the routes for the endpoints
 */

public class RestClient {

    public static RetrofitService getRetrofitService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }
}
