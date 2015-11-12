package com.permutassep.data.repository.datasource.rest.permutassep;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.entity.typeadapter.PostEntityTypeAdapter;
import com.permutassep.data.entity.typeadapter.UserEntityTypeAdapter;

import javax.inject.Inject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class PermutasSEPRestClient {

    private static final String API_BASE_URL = "http://permuta-sep-dev.herokuapp.com/api/";
    private static final String API_REST_USER = "permuta";
    private static final String API_REST_PASSWORD = "Mut4.P3r#Dev_15";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private RestAdapter restAdapter;
    private boolean enablePostTypeAdapter;
    private boolean enableUserTypeAdapter;

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

        Gson gson;

        if (isEnabledPostTypeAdapter() && isEnabledUserTypeAdapter()) {
            gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(PostEntity.class, new PostEntityTypeAdapter())
                    .registerTypeHierarchyAdapter(UserEntity.class, new UserEntityTypeAdapter())
                    .setDateFormat(DATE_FORMAT)
                    .create();
            this.enablePostTypeAdapter = false;
            this.enableUserTypeAdapter = false;
        } else if (isEnabledPostTypeAdapter()) {
            gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(PostEntity.class, new PostEntityTypeAdapter())
                    .setDateFormat(DATE_FORMAT)
                    .create();
            this.enablePostTypeAdapter = false;
        } else if (isEnabledUserTypeAdapter()) {
            gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(UserEntity.class, new UserEntityTypeAdapter())
                    .setDateFormat(DATE_FORMAT)
                    .create();
            this.enableUserTypeAdapter = false;
        } else {
            gson = new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();
        }

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(request -> {
                    String credentials = API_REST_USER + ":" + API_REST_PASSWORD;
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                });

        restAdapter = builder.build();

        return restAdapter.create(PermutasSEPService.class);
    }

    public boolean isEnabledPostTypeAdapter() {
        return enablePostTypeAdapter;
    }

    public boolean isEnabledUserTypeAdapter() {
        return enableUserTypeAdapter;
    }

    public PermutasSEPRestClient enablePostTypeAdapter() {
        this.enablePostTypeAdapter = true;
        return this;
    }

    public PermutasSEPRestClient enableUserTypeAdapter() {
        this.enableUserTypeAdapter = true;
        return this;
    }
}