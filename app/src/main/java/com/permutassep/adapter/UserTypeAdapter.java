package com.permutassep.adapter;

import android.content.Context;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.model.User;
import com.permutassep.utils.Utils;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<User> {

    private Context context;

    public UserTypeAdapter(Context context){
        this.context = context;
    }

	@Override
	public User read(final JsonReader in) throws IOException {
        return Utils.getUser(this.context);
	}

	@Override
	public void write(JsonWriter out, User user) throws IOException {
		 out.value(user.getId());
	}
}