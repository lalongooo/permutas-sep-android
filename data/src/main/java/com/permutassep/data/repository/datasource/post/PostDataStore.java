package com.permutassep.data.repository.datasource.post;

import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPageEntity;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public interface PostDataStore {

    /**
     * Get an {@link Observable} which will emit a List of {@link PostEntity}.
     */
    Observable<List<PostEntity>> getPostsList();

    /**
     * Get an {@link Observable} which will emit a {@link PostEntity}.
     */
    Observable<PostEntity> getPostsDetails(final int postId);

    /**
     * Get an {@link Observable} which will emit a List of {@link PostEntity}.
     */
    Observable<List<PostEntity>> userPosts(final int userId);

    /**
     * Get an {@link Observable} which will emit a List of {@link PostEntity}.
     */
    Observable<List<PostEntity>> searchPosts(final Map<String, String> parameters);

    /**
     * Get an {@link Observable} which will emit a {@link PostPageEntity}.
     */
    Observable<PostPageEntity> getPostPage(final int page, final int pageSize);

    /**
     * Get an {@link Observable} which will emit a {@link PostEntity} after creating a Post in the REST API.
     */
    Observable<PostEntity> newPost(final PostEntity postEntity);
}