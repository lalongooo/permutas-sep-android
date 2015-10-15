package com.permutassep.presentation.view;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.model.UserModel;


public interface SignUpView extends LoadDataView {

    /**
     * The new signed up user
     * @param userModel The user retrieved after the successful sign up process.
     */
    void signedUpUser(UserModel userModel);

    void showDuplicateEmailErrorMessage();

}