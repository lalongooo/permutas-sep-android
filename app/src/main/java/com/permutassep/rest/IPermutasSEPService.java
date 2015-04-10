package com.permutassep.rest;

import com.permutassep.model.Post;
import com.permutassep.model.User;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface  IPermutasSEPService {

    @POST("/users/")
    void newUser(@Body User user, Callback<User> callback);

    @POST("/posts/")
    void newPost(@Body Post post, Callback<Post> callback);
}