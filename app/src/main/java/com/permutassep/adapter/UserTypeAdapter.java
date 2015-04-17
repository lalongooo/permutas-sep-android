package com.permutassep.adapter;

import android.content.Context;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.config.Config;
import com.permutassep.model.User;
import com.permutassep.utils.PrefUtils;

import java.io.IOException;

import br.kots.mob.complex.preferences.ComplexPreferences;

public class UserTypeAdapter extends TypeAdapter<User> {

    private Context context;

    public UserTypeAdapter(Context context){
        this.context = context;
    }

	@Override
	public User read(final JsonReader in) throws IOException {
        return ComplexPreferences.getComplexPreferences(context, Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE).getObject(PrefUtils.PREF_USER_KEY, User.class);
	}

	@Override
	public void write(JsonWriter out, User user) throws IOException {
		 out.value(user.getId());
	}
}