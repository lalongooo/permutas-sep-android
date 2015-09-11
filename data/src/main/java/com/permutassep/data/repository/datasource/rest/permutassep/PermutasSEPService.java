package com.permutassep.data.repository.datasource.rest.permutassep;


import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPageEntity;
import com.permutassep.data.entity.UserEntity;

import java.util.List;
import java.util.Map;

import retrofit.http.GET;
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

}