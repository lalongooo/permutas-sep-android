package com.permutassep.data.repository.datasource.restful.inegifacil;

import retrofit.RestAdapter;


public class InegiFacilRestClient {

    private static String API_BASE_URL = "http://inegifacil.com/";
    private RestAdapter restAdapter;

    public InegiFacilRestClient() {

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(API_BASE_URL);

        restAdapter = builder.build();
    }

    public IInegiFacilService get() {
        return restAdapter.create(IInegiFacilService.class);
    }

}
