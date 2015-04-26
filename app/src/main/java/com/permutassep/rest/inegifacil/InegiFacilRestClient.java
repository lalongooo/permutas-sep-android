package com.permutassep.rest.inegifacil;

import com.permutassep.config.Config;

import retrofit.RestAdapter;

/**
 * Created by jorge.hernandez on 2/24/2015.
 */
public class InegiFacilRestClient {

    private static IInegiFacilService restClient;
    private static String BASE_URL = Config.INEGI_FACIL_REST_BASE_URL;

    static {
        setupRestClient();
    }

    private InegiFacilRestClient() {}

    public static IInegiFacilService get() {
        return restClient;
    }

    private static void setupRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
//                .setClient(new OkClient(new OkHttpClient()))
//                .builder.setLogLevel(RestAdapter.LogLevel.FULL);

        restClient = restAdapter.create(IInegiFacilService.class);
    }
}
