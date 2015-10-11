package com.permutassep.presentation.presenter;

import android.support.annotation.NonNull;

import com.permutassep.domain.Post;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.view.PostDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by lalongooo on 27/09/15.
 **/

@PerActivity
public class PostDetailsPresenter implements Presenter {

    private final UseCase getPostDetailsUseCase;
    private final PostModelDataMapper postModelDataMapper;
    private int postId;
    private PostDetailsView postDetailsView;

    @Inject
    public PostDetailsPresenter(@Named("postDetails") UseCase getPostDetailsUseCase, PostModelDataMapper postModelDataMapper) {
        this.getPostDetailsUseCase = getPostDetailsUseCase;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(@NonNull PostDetailsView postDetailsView) {
        this.postDetailsView = postDetailsView;
    }

    /**
     * Initializes the presenter by start retrieveing the post details
     *
     * @param postId The post unique id
     */
    public void initialize(int postId) {
        this.postId = postId;
        this.loadPostDetails();
    }

    private void loadPostDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPostDetails();
    }

    private void hideViewRetry() {
        this.postDetailsView.hideRetry();
    }

    private void showViewLoading() {
        this.postDetailsView.showLoading();
    }

    private void getPostDetails() {
        this.getPostDetailsUseCase.execute(new PostDetailSubscriber());
    }

    private void hideViewLoading() {
        this.postDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.postDetailsView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.postDetailsView.getContext(), errorBundle.getException());
        this.postDetailsView.showError(errorMessage);
    }

    private void showUserDetailsInView(Post post) {
        final PostModel postModel = this.postModelDataMapper.transform(post);
        this.postDetailsView.renderPost(postModel);
    }

    /**
     * Methods from the DefaultSubscriber<Post>
     */
    private final class PostDetailSubscriber extends DefaultSubscriber<Post> {
        @Override
        public void onCompleted() {
            PostDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PostDetailsPresenter.this.hideViewLoading();
            PostDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PostDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Post post) {
            PostDetailsPresenter.this.showUserDetailsInView(post);
        }

    }

    /**
     * Methods from the {@link Presenter}
     */

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getPostDetailsUseCase.unsubscribe();
    }
}
