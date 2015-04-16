package com.permutassep.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.permutassep.config.Config;
import com.permutassep.model.User;

/**
 * Created by jorge.hernandez on 4/10/2015.
 */
public class PrefUtils {

    /*
    * Preference to store the current user logged
    * in the application (the email and phone of this user can be modified on each post).
    * */
    public static final String PREF_USER_KEY = "user";

    /*
    * Preference to store the current user logged
    * in the application. This user data, cannot be modified
    * */
    public static final String PREF_ORIGINAL_USER_KEY = "user";

    /*
    * Preference to store the searchi results
    * */
    public static final String PREF_SEARCH_RESULTS = "user";

    /*
    * Boolean preference value to indicate that the current user
    * was registered with email/password credentials. If it's false, it means
    * that the user was logged/registered using a social network login.
    * */
    public static final String PREF_IS_NORMAL_USER = "pref_is_normal__user";

    public static void setNormalUser(Context context, boolean isNormalUser) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_IS_NORMAL_USER, isNormalUser).commit();
    }
}
