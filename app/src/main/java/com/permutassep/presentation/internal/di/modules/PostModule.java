package com.permutassep.presentation.internal.di.modules;

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.interactor.GetPostDetails;
import com.permutassep.domain.interactor.GetPostsList;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.domain.repository.PostRepository;
import com.permutassep.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@Module
public class PostModule {

    private int postId = -1;

    public PostModule() {
    }

    public PostModule(int postId) {
        this.postId = postId;
    }

    @Provides
    @PerActivity
    @Named("postList")
    UseCase provideGetPostListUseCase(GetPostsList getPostsList) {
        return getPostsList;
    }

    @Provides
    @PerActivity
    @Named("postDetails")
    UseCase provideGetPostDetailsUseCase(
            PostRepository postRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetPostDetails(postId, postRepository, threadExecutor, postExecutionThread);
    }


}
