package com.permutassep.utils;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.model.User;

public class UserTypeAdapter extends TypeAdapter<User> {

	@Override
	public User read(final JsonReader in) throws IOException {
		return new User();
	}

	@Override
	public void write(JsonWriter out, User user) throws IOException {
		 out.value(user.getId());
	}
}