package com.permutassep.presentation.internal.di.modules;

import com.permutassep.domain.interactor.GetPostsList;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.internal.di.PerActivity;

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

    @Provides
    @PerActivity
    @Named("postList")
    UseCase provideGetPostListUseCase(GetPostsList getPostsList) {
        return getPostsList;
    }
}
