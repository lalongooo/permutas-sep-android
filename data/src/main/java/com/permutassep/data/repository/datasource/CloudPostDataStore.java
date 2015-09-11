package com.permutassep.data.repository.datasource;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPageEntity;
import com.permutassep.data.repository.datasource.rest.permutassep.PermutasSEPRestClient;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * {@link PostDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPostDataStore implements PostDataStore {

    private final PermutasSEPRestClient restClient;

    @Inject
    public CloudPostDataStore() {
        this.restClient = new PermutasSEPRestClient();
    }

    @Override
    public Observable<List<PostEntity>> getPostsList() {
        return restClient.get().getPosts();
    }

    @Override
    public Observable<PostEntity> getPostsDetails(int postId) {
        return restClient.get().getPost(postId);
    }

    @Override
    public Observable<List<PostEntity>> userPosts(int userId) {
        return restClient.get().userPosts(userId);
    }

    @Override
    public Observable<List<PostEntity>> searchPosts(Map<String, String> parameters) {
        return restClient.get().searchPosts(parameters);
    }

    @Override
    public Observable<PostPageEntity> getPostPage(int page, int pageSize) {
        return restClient.get().getPostPage(page, pageSize);
    }
}
