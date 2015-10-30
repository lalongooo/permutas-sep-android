package com.permutassep.data.repository.datasource.rest.permutassep;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class PermutasSEPRestClient {

    private static final String API_BASE_URL = "http://permuta-sep.herokuapp.com/api/";
    private static final String API_REST_USER = "permuta";
    private static final String API_REST_PASSWORD = "Mut4.P3r#oS_15";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private RestAdapter restAdapter;

    @Inject
    public PermutasSEPRestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setConverter(new GsonConverter(gson))
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