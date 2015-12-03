package com.permutassep.presentation.interfaces;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.model.UserModel;
import com.permutassep.ui.ActivityMain;

/**
 * Interface implemented in the {@link ActivityMain},
 * it is used to listen when the login has been successful in the different fragments where
 * a login/sign up is performed.
 */
public interface LoginCompleteListener {
    void onLoginComplete(UserModel userModel);
}
