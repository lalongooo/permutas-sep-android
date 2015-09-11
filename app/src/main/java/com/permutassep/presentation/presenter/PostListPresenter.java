package com.permutassep.presentation.presenter;

import com.permutassep.domain.interactor.UseCase;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class PostListPresenter implements Presenter {

    private final UseCase getPostByUserUseCase;

    @Inject
    public PostListPresenter(@Named("postByUser") UseCase getPostByUserUseCase) {
        this.getPostByUserUseCase = getPostByUserUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
