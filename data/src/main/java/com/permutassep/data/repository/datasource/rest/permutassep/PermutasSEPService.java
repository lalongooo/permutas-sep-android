package com.permutassep.data.repository.datasource.rest.permutassep;


import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPage;
import com.permutassep.data.entity.UserEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface PermutasSEPService {

    @GET("/users/")
    Observable<List<UserEntity>> getUsers();

    @GET("/users/{id}")
    Observable<UserEntity> getUser(@Path("id") int userId);

    @GET("/users/{id}/posts")
    Observable<List<PostEntity>> userPosts(@Path("id") int userId);

    @GET("/posts/")
    Observable<PostPage> getPostPage(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("/users/{id}/posts")
    void userPosts(@Path("id") int id, Callback<List<PostEntity>> callback);

    @GET("/posts/")
    void getPostPage(@Query("page") int page, @Query("page_size") int pageSize, Callback<PostPage> callback);

}