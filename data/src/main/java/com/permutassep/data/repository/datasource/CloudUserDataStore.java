package com.permutassep.data.repository.datasource;

import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.repository.datasource.rest.permutassep.PermutasSEPRestClient;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

    private PermutasSEPRestClient restClient;

    @Inject
    public CloudUserDataStore() {
        this.restClient = new PermutasSEPRestClient();
    }

    @Override
    public Observable<List<UserEntity>> getUsersList() {
        return restClient.get().getUsers();
    }

    @Override
    public Observable<UserEntity> getUserDetails(final int userId) {
        return this.restClient.get().getUser(userId);
    }
}