package com.permutassep.presentation.mapper;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.presentation.model.LoginDataWrapperModel;

public class LoginDataModelDataMapper {


    public LoginDataWrapperModel transform(LoginDataWrapper loginDataWrapper) {

        LoginDataWrapperModel loginDataWrapperModel = null;
        if (loginDataWrapper != null) {
            loginDataWrapperModel = new LoginDataWrapperModel(
                    loginDataWrapper.getEmail(),
                    loginDataWrapper.getPassword()
            );
        }

        return loginDataWrapperModel;
    }
}
