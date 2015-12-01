package com.permutassep.data.entity.mapper;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.LoginDataWrapperEntity;
import com.permutassep.domain.LoginDataWrapper;

import javax.inject.Inject;

public class LoginDataWrapperEntityDataMapper {

    @Inject
    public LoginDataWrapperEntityDataMapper() {
    }

    public LoginDataWrapper transform(LoginDataWrapperEntity loginDataWrapperEntity) {

        LoginDataWrapper loginDataWrapper = null;

        if (loginDataWrapperEntity != null) {
            loginDataWrapper = new LoginDataWrapper(
                    loginDataWrapperEntity.getEmail(),
                    loginDataWrapperEntity.getPassword()
            );
        }

        return loginDataWrapper;
    }

    public LoginDataWrapperEntity transform(LoginDataWrapper loginDataWrapper) {

        LoginDataWrapperEntity loginDataWrapperEntity = null;

        if (loginDataWrapper != null) {
            loginDataWrapperEntity = new LoginDataWrapperEntity(
                    loginDataWrapper.getEmail(),
                    loginDataWrapper.getPassword()
            );
        }

        return loginDataWrapperEntity;
    }

}