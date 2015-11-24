package com.permutassep.data.repository;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.entity.mapper.EmailEntityDataMapper;
import com.permutassep.data.entity.mapper.LoginDataWrapperDataMapper;
import com.permutassep.data.entity.mapper.UserEntityDataMapper;
import com.permutassep.data.repository.datasource.AuthenticationDataStore;
import com.permutassep.data.repository.datasource.AuthenticationDataStoreFactory;
import com.permutassep.domain.Email;
import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.User;
import com.permutassep.domain.repository.AuthenticationRepository;

import javax.inject.Inject;

import rx.Observable;

public class AuthenticationDataRepository implements AuthenticationRepository {


    private AuthenticationDataStoreFactory authenticationDataStoreFactory;
    private LoginDataWrapperDataMapper loginDataWrapperDataMapper;
    private UserEntityDataMapper userEntityDataMapper;
    private EmailEntityDataMapper emailEntityDataMapper;

    @Inject
    public AuthenticationDataRepository(AuthenticationDataStoreFactory authenticationDataStoreFactory, LoginDataWrapperDataMapper loginDataWrapperDataMapper, UserEntityDataMapper userEntityDataMapper, EmailEntityDataMapper emailEntityDataMapper) {
        this.authenticationDataStoreFactory = authenticationDataStoreFactory;
        this.loginDataWrapperDataMapper = loginDataWrapperDataMapper;
        this.userEntityDataMapper = userEntityDataMapper;
        this.emailEntityDataMapper = emailEntityDataMapper;
    }

    @Override
    public Observable<User> login(LoginDataWrapper loginDataWrapper) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.login(loginDataWrapperDataMapper.transform(loginDataWrapper))
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<User> signUp(User user) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.signUp(new UserEntity(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getPhone(), user.getSocialUserId()))
                .map(this.userEntityDataMapper::transform);
    }

    @Override
    public Observable<String> resetPassword(Email email) {
        AuthenticationDataStore authenticationDataStore = authenticationDataStoreFactory.createCloudDataStore();
        return authenticationDataStore.resetPassword(emailEntityDataMapper.transform(email));
    }
}
