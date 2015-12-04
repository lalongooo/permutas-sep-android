package com.permutassep.presentation.presenter;

import com.permutassep.domain.Post;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.GetMyPostsList;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.view.PostsListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class UserPostListPresenter implements Presenter {

    private final GetMyPostsList getMyPostsListUseCase;
    private final PostModelDataMapper postModelDataMapper;
    private PostsListView postsListView;
    private UserModel userModel;

    @Inject
    public UserPostListPresenter(GetMyPostsList getMyPostsListUseCase, PostModelDataMapper postModelDataMapper) {
        this.getMyPostsListUseCase = getMyPostsListUseCase;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(PostsListView postsListView, UserModel userModel) {
        this.postsListView = postsListView;
        this.userModel = userModel;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize(int userId) {
        this.loadUserPostsList(userId);
    }

    /**
     * Loads all users.
     */
    private void loadUserPostsList(int userId) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserPostsList(userId);
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

    private void getUserPostsList(int userId) {
        this.getMyPostsListUseCase.setUserId(userId);
        this.getMyPostsListUseCase.execute(new PostListSubscriber());
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
        for (PostModel postModel : postModelCollection) {
            postModel.setUser(userModel);
        }
        this.postsListView.renderPostList(postModelCollection);
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
        getMyPostsListUseCase.unsubscribe();
    }

    private final class PostListSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            UserPostListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            UserPostListPresenter.this.hideViewLoading();
            UserPostListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserPostListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Post> posts) {
            UserPostListPresenter.this.showUsersCollectionInView(posts);
        }
    }
}