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
    private int mPage;
    private int mPageSize;

    @Inject
    public GetPagedPostsList(PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.postRepository.getPostPage(mPage, mPageSize);
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }
}