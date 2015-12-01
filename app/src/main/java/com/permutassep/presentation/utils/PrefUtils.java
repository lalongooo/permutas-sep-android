package com.permutassep.presentation.utils;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.model.UserModel;

public class PrefUtils {

    /**
     * Preference to store the current user logged
     * in the application (the email and phone of this user can be modified on each post).
     */
    public static final String PREF_USER_KEY = "permutas_sep_user";

    /**
     * Boolean indicating whether we performed the (first-time) drawer opened.
     */
    public static final String PREF_DRAWER_OPENED = "pref_drawer_first_time_opened";

    /**
     * Boolean indicating whether the user has already registered or not.
     */
    public static final String PREF_IS_USER_LOGGED_IN = "pref_drawer_first_time_opened";

    /**
     * Boolean indicating whether ToS has been accepted
     */
    public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";


    public static void markLoggedUser(final Context context, boolean loggedIn) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_IS_USER_LOGGED_IN, loggedIn).apply();
    }

    public static boolean isLoggedUser(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_IS_USER_LOGGED_IN, false);
    }

    public static boolean firstTimeDrawerOpened(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_DRAWER_OPENED, false);
    }

    public static void markFirstTimeDrawerOpened(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_DRAWER_OPENED, true).apply();
    }

    public static void clearApplicationPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void markTosAccepted(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_TOS_ACCEPTED, true).apply();
    }

    public static boolean isTosAccepted(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_TOS_ACCEPTED, false);
    }

    public static UserModel getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String gson = preferences.getString(PREF_USER_KEY, null);
        return new Gson().fromJson(gson, UserModel.class);
    }

    public static void putUser(Context context, UserModel userModel) {
        if (userModel == null) {
            throw new IllegalArgumentException("Parameter userModel is null");
        }
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(PREF_USER_KEY, new Gson().toJson(userModel)).apply();
    }
}
