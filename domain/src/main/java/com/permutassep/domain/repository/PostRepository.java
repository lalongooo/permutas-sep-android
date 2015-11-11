package com.permutassep.domain.repository;


import com.permutassep.domain.Post;
import com.permutassep.domain.PostPage;
import com.permutassep.domain.User;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface PostRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link Post}.
     */
    Observable<List<Post>> getPostsList();

    /**
     * Get an {@link Observable} which will emit a {@link Post}.
     */
    Observable<Post> getPostsDetails(final int postId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Post}.
     */
    Observable<List<Post>> userPosts(final int userId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Post}.
     */
    Observable<List<Post>> searchPosts(final Map<String, String> parameters);

    /**
     * Get an {@link Observable} which will emit a {@link PostPage}.
     */
    Observable<PostPage> getPostPage(final int page, final int pageSize);

    /**
     * Get an {@link Observable} which will emit a {@link Post} after creating a Post in the REST API.
     */
    Observable<Post> newPost(final Post post);
}