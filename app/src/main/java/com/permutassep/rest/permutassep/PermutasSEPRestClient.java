package com.permutassep.rest.permutassep;

import android.util.Base64;

import com.lalongooo.permutassep.BuildConfig;
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
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BuildConfig.PERMUTAS_SEP_REST_BASE_URL);

        if(BuildConfig.DEBUG){
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    String credentials = BuildConfig.PERMUTAS_SEP_REST_AUTH_U + ":" + BuildConfig.PERMUTAS_SEP_REST_AUTH_P;
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                }
            });
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        restAdapter = builder.build();
    }

    public PermutasSEPRestClient(Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BuildConfig.PERMUTAS_SEP_REST_BASE_URL);
        builder.setConverter(converter);

        if(BuildConfig.DEBUG){
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    String credentials = BuildConfig.PERMUTAS_SEP_REST_AUTH_U + ":" + BuildConfig.PERMUTAS_SEP_REST_AUTH_P;
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                }
            });
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        restAdapter = builder.build();
    }

    public IPermutasSEPService get() {
        return restAdapter.create(IPermutasSEPService.class);
    }
}