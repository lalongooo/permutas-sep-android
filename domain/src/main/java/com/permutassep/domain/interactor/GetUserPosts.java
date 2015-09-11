package com.permutassep.domain.interactor;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetUserPosts extends UseCase {

    private final PostRepository postRepository;
    private final int userId;

    @Inject
    public GetUserPosts(int userId, PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
        this.userId = userId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return postRepository.postsByUser(this.userId);
    }
}
