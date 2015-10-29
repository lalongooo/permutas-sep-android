package com.permutassep.presentation.interfaces;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.model.UserModel;

/**
 * Interface implemented in the {@link com.permutassep.presentation.view.activity.ActivityMain},
 * it is used to listen when the login has been successful in the diferent fragments where
 * a login/sign up is performed.
 */
public interface LoginCompleteListener {
    void onLoginComplete(UserModel userModel);
}
