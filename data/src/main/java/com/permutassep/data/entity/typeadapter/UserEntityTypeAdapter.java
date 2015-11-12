package com.permutassep.data.entity.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.data.entity.UserEntity;

import java.io.IOException;

public class UserEntityTypeAdapter extends TypeAdapter<UserEntity> {

    @Override
    public UserEntity read(final JsonReader in) {

        UserEntity userEntity = new UserEntity();

        try {

            in.beginObject();
            String name;

            while (in.hasNext()) {

                name = in.nextName();

                switch (name) {

                    case "id":
                        userEntity.setId(Integer.valueOf(in.nextString()));
                        break;
                    case "name":
                        userEntity.setName(in.nextString());
                        break;
                    case "email":
                        userEntity.setEmail(in.nextString());
                        break;
                    case "phone":
                        userEntity.setPhone(in.nextString());
                        break;
                    case "social_user_id":
                        userEntity.setSocialUserId(in.nextString());
                        break;
                    case "date_joined":
                        in.nextString();
                        break;
                }
            }

            in.endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userEntity;
    }

    @Override
    public void write(JsonWriter out, UserEntity user) throws IOException {
        out.value(user.getId());
    }
}