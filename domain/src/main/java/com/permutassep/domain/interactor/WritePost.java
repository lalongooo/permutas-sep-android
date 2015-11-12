package com.permutassep.domain.interactor;

import com.permutassep.domain.Post;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class WritePost extends UseCase {

    private Post post;
    private PostRepository postRepository;


    public WritePost(Post post, PostRepository postRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.post = post;
        this.postRepository = postRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return postRepository.newPost(post);
    }
}
