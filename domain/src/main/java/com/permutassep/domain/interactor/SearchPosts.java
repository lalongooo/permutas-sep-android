package com.permutassep.domain.interactor;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;

public class SearchPosts extends UseCase {

    private final HashMap<String, String> searchParams;
    private final PostRepository postRepository;

    @Inject
    public SearchPosts(HashMap<String, String> searchParams, PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.searchParams = searchParams;
        this.postRepository = postRepository;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        return postRepository.searchPosts(searchParams);
    }
}
