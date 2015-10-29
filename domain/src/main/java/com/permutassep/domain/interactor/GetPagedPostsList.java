package com.permutassep.domain.interactor;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetPagedPostsList extends UseCase {

    private final PostRepository postRepository;
    private final int mPage;
    private final int mPageSize;

    @Inject
    public GetPagedPostsList(int page, int pageSize, PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
        this.mPage = page;
        this.mPageSize = pageSize;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.postRepository.getPostPage(mPage, mPageSize);
    }
}