package com.permutassep.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.permutassep.config.Config;

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
    * Boolean preference value to indicate that the current user
    * was registered with email/password credentials. If it's false, it means
    * that the user was logged/registered using a social network login.
    * */
    public static final String PREF_IS_NORMAL_USER = "pref_is_normal__user";

    /*
    * Boolean indicating whether we performed the (first-time) drawer opened.
    * */
    public static final String PREF_DRAWER_OPENED = "pref_drawer_first_time_opened";

    /*
    * Boolean indicating that the news feed should be reloaded
    * */
    public static final String PREF_RELOAD_NEWS_FEED = "pref_reload_news_feed";

    /** Boolean indicating whether ToS has been accepted */
    public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";

    public static boolean firstTimeDrawerOpened(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_DRAWER_OPENED, false);
    }

    public static void markFirstTimeDrawerOpened(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_DRAWER_OPENED, true).commit();
    }

    public static boolean shouldReloadNewsFeed(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(PREF_RELOAD_NEWS_FEED, false);
    }

    public static void markNewsFeedToReload(final Context context, boolean reloaded) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_RELOAD_NEWS_FEED, reloaded).commit();
    }

    public static void setNormalUser(Context context, boolean isNormalUser) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREF_IS_NORMAL_USER, isNormalUser).commit();
    }

    public static void clearApplicationPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public static void markTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_TOS_ACCEPTED, true).commit();
    }

    public static boolean isTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_TOS_ACCEPTED, false);
    }
}
