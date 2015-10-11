package com.permutassep.presentation.presenter;

import com.permutassep.domain.Post;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.view.PostsListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class PostListPresenter implements Presenter {

    private final UseCase getPostByUserUseCase;
    private final PostModelDataMapper postModelDataMapper;
    private PostsListView postsListView;

    @Inject
    public PostListPresenter(@Named("postList") UseCase getPostsList, PostModelDataMapper postModelDataMapper) {
        this.getPostByUserUseCase = getPostsList;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(PostsListView postsListView) {
        this.postsListView = postsListView;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        getPostByUserUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadPostList();
    }

    /**
     * Loads all users.
     */
    private void loadPostList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPostList();
    }

    public void onPostClicked(PostModel postModel) {
        this.postsListView.viewPostDetail(postModel);
    }

    private void hideViewRetry() {
        this.postsListView.hideRetry();
    }

    private void showViewLoading() {
        this.postsListView.showLoading();
    }

    private void getPostList() {
        this.getPostByUserUseCase.execute(new PostListSubscriber());
    }

    private void hideViewLoading() {
        this.postsListView.hideLoading();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.postsListView.getContext(), errorBundle.getException());
        this.postsListView.showError(errorMessage);
    }

    private void showViewRetry() {
        this.postsListView.showRetry();
    }

    private void showUsersCollectionInView(Collection<Post> postCollection) {
        final Collection<PostModel> postModelCollection = this.postModelDataMapper.transform(postCollection);
        this.postsListView.renderPostList(postModelCollection);
    }

    private final class PostListSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            PostListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PostListPresenter.this.hideViewLoading();
            PostListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PostListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Post> posts) {
            PostListPresenter.this.showUsersCollectionInView(posts);
        }
    }
}