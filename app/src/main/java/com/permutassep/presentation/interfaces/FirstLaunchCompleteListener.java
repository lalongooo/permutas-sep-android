package com.permutassep.presentation.interfaces;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.ui.ActivityMain;

/**
 * Interface implemented in the {@link ActivityMain},
 * it is used to show the navigation drawer only once after the first login/sign up.
 */
public interface FirstLaunchCompleteListener {
    void onFirstLaunchComplete();
}
