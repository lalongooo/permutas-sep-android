package com.permutassep.data.repository;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.mapper.LoginDataWrapperDataMapper;
import com.permutassep.data.entity.mapper.UserEntityDataMapper;
import com.permutassep.data.repository.datasource.AuthenticationDataStore;
import com.permutassep.data.repository.datasource.AuthenticationDataStoreFactory;
import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.User;
import com.permutassep.domain.repository.AuthenticationRepository;

import javax.inject.Inject;

import rx.Observable;

public class AuthenticationDataRepository implements AuthenticationRepository {


    private final AuthenticationDataStoreFactory authenticationDataStoreFactory;
    private final LoginDataWrapperDataMapper loginDataWrapperDataMapper;
    private final UserEntityDataMapper userEntityDataMapper;

    @Inject
    public AuthenticationDataRepository(AuthenticationDataStoreFactory authenticationDataStoreFactory, LoginDataWrapperDataMapper loginDataWrapperDataMapper, UserEntityDataMapper userEntityDataMapper) {
        this.authenticationDataStoreFactory = authenticationDataStoreFactory;
        this.loginDataWrapperDataMapper = loginDataWrapperDataMapper;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<User> login(LoginDataWrapper loginDataWrapper) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.login(loginDataWrapperDataMapper.transform(loginDataWrapper))
                .map(this.userEntityDataMapper::transform);
    }
}
