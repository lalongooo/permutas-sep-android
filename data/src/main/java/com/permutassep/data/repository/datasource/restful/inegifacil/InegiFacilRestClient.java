package com.permutassep.data.repository.datasource.restful.inegifacil;

import com.permutassep.data.BuildConfig;

import retrofit.RestAdapter;


public class InegiFacilRestClient {

    private static String API_BASE_URL = "http://inegifacil.com/";
    private RestAdapter restAdapter;

    public InegiFacilRestClient() {

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(API_BASE_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);

        restAdapter = builder.build();
    }

    public IInegiFacilService get() {
        return restAdapter.create(IInegiFacilService.class);
    }

}
