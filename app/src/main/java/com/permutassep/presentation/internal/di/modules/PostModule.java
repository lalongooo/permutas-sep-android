package com.permutassep.presentation.internal.di.modules;

import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.interactor.GetMyPostsList;
import com.permutassep.domain.interactor.GetPostDetails;
import com.permutassep.domain.interactor.GetPostsList;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.domain.repository.PostRepository;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.model.PostModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@Module
public class PostModule {

    public PostModule() {
    }

    /**
     * This id is used to identify a unique post and a unique user
     */
    private int id = -1;
    public PostModule(int id) {
        this.id = id;
    }

    private PostModel postModel;
    public PostModule(PostModel postModel){
        this.postModel = postModel;
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
        return new GetPostDetails(id, postRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("myPostsList")
    UseCase provideGetMyPostsListUseCase(
            PostRepository postRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetMyPostsList(id, postRepository, threadExecutor, postExecutionThread);
    }
}
