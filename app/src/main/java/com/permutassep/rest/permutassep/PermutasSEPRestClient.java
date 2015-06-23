package com.permutassep.rest.permutassep;

import android.util.Base64;

import com.lalongooo.permutassep.BuildConfig;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

public class PermutasSEPRestClient {

    private static IPermutasSEPService restClient;
    private RestAdapter restAdapter;

    public PermutasSEPRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BuildConfig.com_permutassep_api_rest_endpoint);
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String credentials = BuildConfig.com_permutassep_api_rest_user + ":" + BuildConfig.com_permutassep_api_rest_password;
                request.addHeader("Accept", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
            }
        });

        if(BuildConfig.DEBUG){
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        restAdapter = builder.build();
    }

    public PermutasSEPRestClient(Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BuildConfig.com_permutassep_api_rest_endpoint);
        builder.setConverter(converter);
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String credentials = BuildConfig.com_permutassep_api_rest_user + ":" + BuildConfig.com_permutassep_api_rest_password;
                request.addHeader("Accept", "application/json");
                request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
            }
        });

        if(BuildConfig.DEBUG){
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        restAdapter = builder.build();
    }

    public IPermutasSEPService get() {
        return restAdapter.create(IPermutasSEPService.class);
    }
}