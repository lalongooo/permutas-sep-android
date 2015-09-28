package com.permutassep.domain.interactor;

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import rx.Observable;

/**
 * Created by lalongooo on 27/09/15.
 **/

public class GetPostDetails extends UseCase {

    private int postId = -1;
    private PostRepository postRepository;

    public GetPostDetails(int postId, PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postId = postId;
        this.postRepository = postRepository;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable buildUseCaseObservable() {
        return postRepository.getPostsDetails(postId);
    }
}