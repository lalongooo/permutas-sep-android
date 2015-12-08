package com.permutassep.presentation.utils;

import com.parse.ParseInstallation;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class ParseUtils {

    public static void setUpParseInstallationUser(int userId) {

        String PARSE_INSTALLATION_COLUMN_PS_USER = "PSUser";
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if (installation.get(PARSE_INSTALLATION_COLUMN_PS_USER) == null
                ||
                Integer.valueOf(installation.get(PARSE_INSTALLATION_COLUMN_PS_USER).toString()) != userId) {
            installation.put(PARSE_INSTALLATION_COLUMN_PS_USER, userId);
            installation.saveInBackground();
        }
    }
}
