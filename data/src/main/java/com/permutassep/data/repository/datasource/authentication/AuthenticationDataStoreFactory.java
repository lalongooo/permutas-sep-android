package com.permutassep.data.repository.datasource.authentication;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import javax.inject.Inject;


public class AuthenticationDataStoreFactory {

    @Inject
    public AuthenticationDataStoreFactory() {
    }

    public AuthenticationDataStore createCloudDataStore() {
        return new CloudAuthenticationDataStore();
    }
}