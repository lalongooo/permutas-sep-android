package com.permutassep.rest.permutassep;

import com.permutassep.config.Config;

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
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Config.PERMUTAS_SEP_REST_BASE_URL)
                .build();
    }

    public PermutasSEPRestClient(Converter converter) {
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Config.PERMUTAS_SEP_REST_BASE_URL)
                .setConverter(converter)
                .build();
    }

    public IPermutasSEPService get() {
        return restAdapter.create(IPermutasSEPService.class);
    }
}
