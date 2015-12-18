package com.permutassep.presentation.utils;

import com.parse.ParseInstallation;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class ParseUtils {

    private static final String PARSE_INSTALLATION_COLUMN_PS_USER = "PSUser";

    public static void setUpParseInstallationUser(int userId) {

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(PARSE_INSTALLATION_COLUMN_PS_USER, userId);
        installation.saveEventually();
//        if (installation.get(PARSE_INSTALLATION_COLUMN_PS_USER) == null
//                ||
//                Integer.valueOf(installation.get(PARSE_INSTALLATION_COLUMN_PS_USER).toString()) != userId) {
//            installation.put(PARSE_INSTALLATION_COLUMN_PS_USER, userId);
//            installation.saveInBackground();
//        }
    }

    public static void clearParseInstallationUser() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(PARSE_INSTALLATION_COLUMN_PS_USER, -1);
        installation.saveEventually();
    }
}
