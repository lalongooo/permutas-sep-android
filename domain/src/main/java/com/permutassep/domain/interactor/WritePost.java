package com.permutassep.domain.interactor;

import com.permutassep.domain.Post;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class WritePost extends UseCase {

    private Post post;
    private PostRepository postRepository;

    @Inject
    public WritePost(PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return postRepository.newPost(post);
    }

    public void setPost(Post post) {
        this.post = post;
    }
}