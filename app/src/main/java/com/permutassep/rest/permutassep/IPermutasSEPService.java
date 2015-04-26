package com.permutassep.rest.permutassep;

import com.permutassep.model.AuthModel;
import com.permutassep.model.Post;
import com.permutassep.model.User;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

public interface  IPermutasSEPService {

    @POST("/users/")
    void newUser(@Body User user, Callback<User> callback);

    @POST("/posts/")
    void newPost(@Body Post post, Callback<Post> callback);

    @GET("/posts/")
    void getPosts(Callback<List<Post>> callback);

    @GET("/posts/")
    void searchPosts(@QueryMap Map<String, String> parameters, Callback<List<Post>> callback);

    @POST("/login/")
    void login(@Body AuthModel authModel, Callback<User> callback);
}