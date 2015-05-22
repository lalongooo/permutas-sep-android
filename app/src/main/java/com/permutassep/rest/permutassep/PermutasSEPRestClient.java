package com.permutassep.rest.permutassep;

import android.util.Base64;

import com.permutassep.config.Config;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

/**
 * Created by jorge.hernandez on 2/24/2015.
 */
public class PermutasSEPRestClient {

    private static IPermutasSEPService restClient;
    private RestAdapter restAdapter;

    public PermutasSEPRestClient() {
        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = Config.PERMUTAS_SEP_REST_AUTH_U + ":" + Config.PERMUTAS_SEP_REST_AUTH_P;
                        // Create Base64 encoded string
                        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", string);
                    }
                })
                .setEndpoint(Config.PERMUTAS_SEP_REST_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    public PermutasSEPRestClient(Converter converter) {
        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = Config.PERMUTAS_SEP_REST_AUTH_U + ":" + Config.PERMUTAS_SEP_REST_AUTH_P;
                        // Create Base64 encoded string
                        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", string);
                    }
                })
                .setEndpoint(Config.PERMUTAS_SEP_REST_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(converter)
                .build();
    }

    public IPermutasSEPService get() {
        return restAdapter.create(IPermutasSEPService.class);
    }
}