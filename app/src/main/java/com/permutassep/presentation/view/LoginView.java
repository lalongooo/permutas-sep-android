package com.permutassep.presentation.view;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.model.UserModel;


public interface  LoginView extends LoadDataView {

    /**
     * Authorize a user to log in the app
     * @param userModel The user retrieved after the successful authentication.
     */
    void authorizeUser(UserModel userModel);

}