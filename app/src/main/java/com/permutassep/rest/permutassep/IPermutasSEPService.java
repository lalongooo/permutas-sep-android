package com.permutassep.rest.permutassep;

import com.permutassep.model.AuthModel;
import com.permutassep.model.ConfirmPasswordReset;
import com.permutassep.model.Email;
import com.permutassep.model.Post;
import com.permutassep.model.PostPage;
import com.permutassep.model.User;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface  IPermutasSEPService {

    @POST("/users/")
    void newUser(@Body User user, Callback<User> callback);

    @GET("/users/{id}/posts")
    void myPosts(@Path("id") int id, Callback<List<Post>> callback);

    @POST("/posts/")
    void newPost(@Body Post post, Callback<Post> callback);

    @GET("/posts/")
    void searchPosts(@QueryMap Map<String, String> parameters, Callback<List<Post>> callback);

    @GET("/posts/")
    void getPostPage(@Query("page") int page, @Query("page_size") int pageSize, Callback<PostPage> callback);

    @POST("/login/")
    void login(@Body AuthModel authModel, Callback<User> callback);

    @PATCH("/users/{id}")
    void updateUser(@Path("id") int id, @Body User user, Callback<User> callback);

    @POST("/reset-password/")
    void resetPassword(@Body Email email, ResponseCallback responseCallback);

    @POST("/confirm-reset-password/")
    void confirmPasswordReset(@Body ConfirmPasswordReset cpr, ResponseCallback responseCallback);
}