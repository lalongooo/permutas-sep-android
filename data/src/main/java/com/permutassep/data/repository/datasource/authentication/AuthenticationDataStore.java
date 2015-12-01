package com.permutassep.data.repository.datasource.authentication;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.LoginDataWrapperEntity;
import com.permutassep.data.entity.UserEntity;

import rx.Observable;

public interface AuthenticationDataStore {

    Observable<UserEntity> login(LoginDataWrapperEntity loginDataWrapperEntity);

    Observable<UserEntity> signUp(UserEntity userEntity);
}
