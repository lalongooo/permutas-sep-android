package com.permutassep.data.repository;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.entity.mapper.LoginDataWrapperEntityDataMapper;
import com.permutassep.data.entity.mapper.UserEntityDataMapper;
import com.permutassep.data.repository.datasource.authentication.AuthenticationDataStore;
import com.permutassep.data.repository.datasource.authentication.AuthenticationDataStoreFactory;
import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.User;
import com.permutassep.domain.repository.AuthenticationRepository;

import javax.inject.Inject;

import rx.Observable;

public class AuthenticationDataRepository implements AuthenticationRepository {

    private AuthenticationDataStoreFactory authenticationDataStoreFactory;
    private LoginDataWrapperEntityDataMapper loginDataWrapperEntityDataMapper;
    private UserEntityDataMapper userEntityDataMapper;

    @Inject
    public AuthenticationDataRepository(AuthenticationDataStoreFactory authenticationDataStoreFactory, LoginDataWrapperEntityDataMapper loginDataWrapperEntityDataMapper, UserEntityDataMapper userEntityDataMapper) {
        this.authenticationDataStoreFactory = authenticationDataStoreFactory;
        this.loginDataWrapperEntityDataMapper = loginDataWrapperEntityDataMapper;
        this.userEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<User> login(LoginDataWrapper loginDataWrapper) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.login(loginDataWrapperEntityDataMapper.transform(loginDataWrapper))
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> signUp(User user) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.signUp(new UserEntity(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getPhone(), user.getSocialUserId()))
                .map(this.userEntityDataMapper::transform);
    }
}
