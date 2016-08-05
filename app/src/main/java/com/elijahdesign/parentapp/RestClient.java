package com.elijahdesign.parentapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elijah on 7/28/2016.
 */
public class RestClient {

    public JSONData apiService;

    RestClient() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://turntotech.firebaseio.com/digitalleash/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(JSONData.class);
    }

    public JSONData getApiService() {
        return apiService;
    }
}
