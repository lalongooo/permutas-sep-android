package com.permutassep.data.repository.datasource.authentication;

import javax.inject.Inject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PasswordResetDataStoreFactory {

    @Inject
    public PasswordResetDataStoreFactory() {
    }

    public PasswordResetDataStore createCloudDataStore() {
        return new CloudPasswordResetDataStore();
    }

}