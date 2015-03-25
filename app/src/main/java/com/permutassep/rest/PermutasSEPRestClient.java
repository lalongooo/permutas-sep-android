package com.permutassep.rest;

import com.permutassep.config.Config;

import retrofit.RestAdapter;

/**
 * Created by jorge.hernandez on 2/24/2015.
 */
public class PermutasSEPRestClient {

    private static IPermutasSEPService restClient;
    private static String BASE_URL = Config.PERMUTAS_SEP_REST_BASE_URL;

    static {
        setupRestClient();
    }

    private PermutasSEPRestClient() {}

    public static IPermutasSEPService get() {
        return restClient;
    }

    private static void setupRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();
        restClient = restAdapter.create(IPermutasSEPService.class);
    }
}
