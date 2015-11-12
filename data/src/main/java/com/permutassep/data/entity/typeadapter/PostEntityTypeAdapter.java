package com.permutassep.data.entity.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.permutassep.data.entity.PostEntity;

import java.io.IOException;

public class PostEntityTypeAdapter extends TypeAdapter<PostEntity> {

    @Override
    public PostEntity read(final JsonReader in) {

        PostEntity p = new PostEntity();

        try {

            in.beginObject();

            while (in.hasNext()) {

                String name = in.nextName();
                switch (name) {
                    case "id":
                        p.setId(in.nextInt());
                        break;
                    case "workday_type":
                        p.setWorkdayType(in.nextString());
                        break;
                    case "position_type":
                        p.setPositionType(in.nextString());
                        break;
                    case "post_date":
                        p.setPostDate(in.nextString());
                        break;
                    case "is_teaching_career":
                        p.setIsTeachingCareer(in.nextBoolean());
                        break;
                    case "academic_level":
                        p.setAcademicLevel(in.nextString());
                        break;
                    case "post":
                        p.setPostText(in.nextString());
                        break;
                    case "place_from_lon":
                        p.setLonFrom(in.nextString());
                        break;
                    case "place_from_lat":
                        p.setLatFrom(in.nextString());
                        break;
                    case "place_from_state":
                        p.setStateFrom(in.nextString());
                        break;
                    case "place_from_city":
                        p.setCityFrom(in.nextString());
                        break;
                    case "place_from_town":
                        p.setTownFrom(in.nextString());
                        break;
                    case "place_to_lon":
                        p.setLonTo(in.nextString());
                        break;
                    case "place_to_lat":
                        p.setLatTo(in.nextString());
                        break;
                    case "place_to_state":
                        p.setStateTo(in.nextString());
                        break;
                    case "place_to_city":
                        p.setCityTo(in.nextString());
                        break;
                    case "place_to_town":
                        p.setTownTo(in.nextString());
                        break;
                    case "user":
                        in.nextInt();
                        break;
                }
            }
            in.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p;
    }

    @Override
    public void write(JsonWriter out, PostEntity post) {

        try {

            out.beginObject();

            out.name("workday_type").value(post.getWorkdayType());
            out.name("position_type").value(post.getPositionType());
            out.name("post_date").value(post.getPostDate());
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
            out.name("user").value("");

            out.endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}