package com.permutassep.data.repository;

import com.permutassep.data.entity.mapper.PostEntityDataMapper;
import com.permutassep.data.entity.mapper.PostPageEntityDataMapper;
import com.permutassep.data.repository.datasource.post.PostDataStore;
import com.permutassep.data.repository.datasource.post.PostDataStoreFactory;
import com.permutassep.domain.Post;
import com.permutassep.domain.PostPage;
import com.permutassep.domain.repository.PostRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PostDataRepository implements PostRepository {

    private final PostDataStoreFactory postDataStoreFactory;
    private final PostEntityDataMapper postEntityDataMapper;
    private final PostPageEntityDataMapper postPageEntityDataMapper;

    @Inject
    public PostDataRepository(PostDataStoreFactory postDataStoreFactory, PostEntityDataMapper postEntityDataMapper, PostPageEntityDataMapper postPageEntityDataMapper) {
        this.postDataStoreFactory = postDataStoreFactory;
        this.postEntityDataMapper = postEntityDataMapper;
        this.postPageEntityDataMapper = postPageEntityDataMapper;
    }

    @Override
    public Observable<List<Post>> getPostsList() {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.getPostsList()
                .map(this.postEntityDataMapper::transform);
    }

    @Override
    public Observable<Post> getPostsDetails(int postId) {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.getPostsDetails(postId)
                .map(postEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Post>> userPosts(int userId) {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.userPosts(userId)
                .map(this.postEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Post>> searchPosts(Map<String, String> parameters) {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.searchPosts(parameters)
                .map(this.postEntityDataMapper::transform);
    }

    @Override
    public Observable<PostPage> getPostPage(int page, int pageSize) {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.getPostPage(page, pageSize)
                .map(this.postPageEntityDataMapper::transform);
    }

    @Override
    public Observable<Post> newPost(Post post) {
        final PostDataStore postDataStore = postDataStoreFactory.createCloudDataStore();
        return postDataStore.newPost(postEntityDataMapper.transform(post))
                .map(postEntityDataMapper::transform);
    }
}