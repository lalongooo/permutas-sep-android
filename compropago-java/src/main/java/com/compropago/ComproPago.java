package com.compropago;

import java.lang.reflect.Field;

public class ComproPago {

    public static void main(String[] a) {
        System.out.println(System.getProperty("com_permutassep_debug_api_rest_endpoint"));
    }

    public String getVariable() {
        String propertyValue = null;

        try {
            Class<?> klass = Class.forName("com.lalongooo.permutassep.BuildConfig");

            Field field = klass.getDeclaredField("com_permutassep_api_rest_password");

            propertyValue = (String) field.get(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return propertyValue;
    }
}
