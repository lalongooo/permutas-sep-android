package com.permutassep.domain.repository;


import com.permutassep.domain.Post;
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
    Observable<List<Post>> postsByUser(final int userId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Post}.
     */
    Observable<List<Post>> searchPosts(final Map<String, String> parameters);
}
