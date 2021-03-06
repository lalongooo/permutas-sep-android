package com.permutassep.data.repository.datasource.restful.permutassep;


import com.permutassep.data.entity.ConfirmPasswordResetEntity;
import com.permutassep.data.entity.EmailEntity;
import com.permutassep.data.entity.LoginDataWrapperEntity;
import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPageEntity;
import com.permutassep.data.entity.UserEntity;

import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface PermutasSEPService {

    @GET("/users/")
    Observable<List<UserEntity>> getUsers();

    @GET("/users/{id}")
    Observable<UserEntity> getUser(@Path("id") int userId);

    @GET("/posts/")
    Observable<List<PostEntity>> getPosts();

    @GET("/posts/{id}")
    Observable<PostEntity> getPost(@Path("id") int postId);

    @GET("/users/{id}/posts")
    Observable<List<PostEntity>> userPosts(@Path("id") int userId);

    @GET("/posts/")
    Observable<PostPageEntity> getPostPage(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("/posts/")
    Observable<List<PostEntity>> searchPosts(@QueryMap Map<String, String> parameters);

    @POST("/login/")
    Observable<UserEntity> login(@Body LoginDataWrapperEntity loginDataWrapperEntity);

    @POST("/users/")
    Observable<UserEntity> signUp(@Body UserEntity userEntity);

    @POST("/posts/")
    Observable<PostEntity> newPost(@Body PostEntity post);

    @POST("/reset-password/")
    Observable<Response> resetPassword(@Body EmailEntity email);

    @POST("/confirm-reset-password/")
    Observable<Response> confirmPasswordReset(@Body ConfirmPasswordResetEntity confirmPasswordResetEntity);
}