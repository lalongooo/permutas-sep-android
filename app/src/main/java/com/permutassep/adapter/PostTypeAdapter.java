package com.permutassep.adapter;

import android.content.Context;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.config.Config;
import com.permutassep.model.Post;
import com.permutassep.model.User;
import com.permutassep.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostTypeAdapter extends TypeAdapter<Post> {

    private Context context;

    public PostTypeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Post read(final JsonReader in) throws IOException {

        Post p = new Post();

        try {

            in.beginObject();

            while (in.hasNext()) {

                String name = in.nextName();


                if (name.equals("id")) {
                    p.setId(in.nextInt());
                } else if (name.equals("workday_type")) {
                    p.setWorkdayType(in.nextString());
                } else if (name.equals("position_type")) {
                    p.setPositionType(in.nextString());
                } else if (name.equals("post_date")) {
                    SimpleDateFormat formatter = new SimpleDateFormat(Config.APP_DATE_FORMAT);
                    p.setPostDate(formatter.parse(in.nextString()));
                } else if (name.equals("is_teaching_career")) {
                    p.setIsTeachingCareer(in.nextBoolean());
                } else if (name.equals("academic_level")) {
                    p.setAcademicLevel(in.nextString());
                } else if (name.equals("post")) {
                    p.setPostText(in.nextString());
                } else if (name.equals("place_from_lon")) {
                    p.setLonFrom(in.nextString());
                } else if (name.equals("place_from_lat")) {
                    p.setLatFrom(in.nextString());
                } else if (name.equals("place_from_state")) {
                    p.setStateFrom(Short.valueOf(in.nextString()));
                } else if (name.equals("place_from_city")) {
                    p.setCityFrom(Short.valueOf(in.nextString()));
                } else if (name.equals("place_from_town")) {
                    p.setTownFrom(Short.valueOf(in.nextString()));
                } else if (name.equals("place_to_lon")) {
                    p.setLonTo(in.nextString());
                } else if (name.equals("place_to_lat")) {
                    p.setLatTo(in.nextString());
                } else if (name.equals("place_to_state")) {
                    p.setStateTo(Short.valueOf(in.nextString()));
                } else if (name.equals("place_to_city")) {
                    p.setCityTo(Short.valueOf(in.nextString()));
                } else if (name.equals("place_to_town")) {
                    p.setTownTo(Short.valueOf(in.nextString()));
                } else if (name.equals("user")) {
                    in.nextInt();
                    p.setUser(Utils.getUser(this.context));
                }

            }

            in.endObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return p;
    }

    @Override
    public void write(JsonWriter out, Post post) throws IOException {

        out.beginObject();
        out.name("workday_type").value(post.getWorkdayType());
        out.name("position_type").value(post.getPositionType());

        SimpleDateFormat format = new SimpleDateFormat(Config.APP_DATE_FORMAT);
        out.name("post_date").value(format.format(post.getPostDate()));

        out.name("is_teaching_career").value(post.isTeachingCareer());
        out.name("post").value(post.getPostText());
        out.name("place_from_lon").value(post.getLonFrom());
        out.name("place_from_lat").value(post.getLatFrom());
        out.name("place_from_state").value(post.getStateFrom());
        out.name("place_from_city").value(post.getCityFrom());
        out.name("place_from_town").value(post.getTownFrom());
        out.name("place_to_lon").value(post.getLonTo());
        out.name("place_to_lat").value(post.getLatTo());
        out.name("place_to_state").value(post.getStateTo());
        out.name("place_to_city").value(post.getCityTo());
        out.name("place_to_town").value(post.getTownTo());
        out.name("academic_level").value(post.getAcademicLevel());

        User u = Utils.getUser(this.context);
        out.name("user").value(u.getId());

        out.endObject();
    }
}