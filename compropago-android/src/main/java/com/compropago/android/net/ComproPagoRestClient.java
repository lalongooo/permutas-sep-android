package com.compropago.android.net;

import android.util.Base64;

import com.compropago.android.BuildConfig;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ComproPagoRestClient {

    private RestAdapter restAdapter;

    public ComproPagoRestClient() {

        RestAdapter.Builder builder = new RestAdapter
                .Builder()
                .setEndpoint(BuildConfig.COMPROPAGO_API_HOST)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String credentials = BuildConfig.COMPROPAGO_API_PUBLIC_KEY + ":";
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", BuildConfig.DEBUG ? "Basic c2tfdGVzdF80MTMxODg1MGFlMjY0NDk1Yjo=" : "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
                    }
                });

        restAdapter = builder.build();

    }

    public ComproPagoService get() {
        return restAdapter.create(ComproPagoService.class);
    }
}
