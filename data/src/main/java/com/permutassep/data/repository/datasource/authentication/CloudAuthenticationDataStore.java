package com.permutassep.data.repository.datasource.authentication;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.LoginDataWrapperEntity;
import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.repository.datasource.restful.permutassep.PermutasSEPRestClient;

import javax.inject.Inject;

import rx.Observable;

public class CloudAuthenticationDataStore implements AuthenticationDataStore {

    private final PermutasSEPRestClient restClient;

    @Inject
    public CloudAuthenticationDataStore() {
        restClient = new PermutasSEPRestClient();
    }


    @Override
    public Observable<UserEntity> login(LoginDataWrapperEntity loginDataWrapperEntity) {
        return restClient.get().login(loginDataWrapperEntity);
    }

    @Override
    public Observable<UserEntity> signUp(UserEntity userEntity) {
        return restClient.get().signUp(userEntity);
    }
}