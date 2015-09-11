package com.permutassep.data.repository.datasource.rest.permutassep;

import android.util.Base64;

import javax.inject.Inject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class PermutasSEPRestClient {

    private static final String API_BASE_URL = "http://permuta-sep-dev.herokuapp.com/api/";
    private static final String API_REST_USER = "permuta";
    private static final String API_REST_PASSWORD = "kc.ll#15";
    private static PermutasSEPService restClient;
    private RestAdapter restAdapter;

    @Inject
    public PermutasSEPRestClient() {
        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = API_REST_USER + ":" + API_REST_PASSWORD;
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                    }
                });

        restAdapter = builder.build();
    }

    public PermutasSEPService get() {
        return restAdapter.create(PermutasSEPService.class);
    }
}