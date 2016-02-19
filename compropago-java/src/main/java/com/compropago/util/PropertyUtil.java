package com.compropago.util;

import java.lang.reflect.Field;

/**
 * By Jorge E. Hernandez (@lalongooo) 2016
 */
public class PropertyUtil {

    public static final String BUILD_CONFIG_PKG_NAME = "com.lalongooo.permutassep.BuildConfig";

    public static final String COMPROPAGO_API_HOST = "COMPROPAGO_API_HOST";
    public static final String COMPROPAGO_API_PUBLIC_KEY = "COMPROPAGO_API_PUBLIC_KEY";
    public static final String COMPROPAGO_API_PASSWORD = "COMPROPAGO_API_PASSWORD";


    public String getProperty(String propertyName) {
        String propertyValue = null;

        try {
            Class<?> klass = Class.forName(BUILD_CONFIG_PKG_NAME);

            Field field = klass.getDeclaredField(propertyName);

            propertyValue = (String) field.get(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return propertyValue;
    }


}