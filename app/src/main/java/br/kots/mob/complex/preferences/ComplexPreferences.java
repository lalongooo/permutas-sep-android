package br.kots.mob.complex.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.permutassep.presentation.config.Config;

public class ComplexPreferences {

    private static ComplexPreferences complexPreferences;
    private SharedPreferences preferences;
    private static Gson GSON = new Gson();

    private ComplexPreferences(Context context) {
        preferences = context.getSharedPreferences(Config.APP_PREFERENCES_NAME, 0);
    }

    public static ComplexPreferences get(Context context) {

        if (complexPreferences == null) {
            complexPreferences = new ComplexPreferences(context);
        }

        return complexPreferences;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is empty or null");
        }

        preferences.edit().putString(key, GSON.toJson(object)).apply();
    }

    public <T> T getObject(String key, Class<T> a) {

        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object saved with key " + key + " is instanceof another class");
            }
        }
    }


}
