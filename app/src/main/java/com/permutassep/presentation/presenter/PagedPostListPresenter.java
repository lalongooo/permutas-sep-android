package com.permutassep.presentation.presenter;

import com.permutassep.domain.Post;
import com.permutassep.domain.PostPage;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.mapper.PostPageModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.model.PostPageModel;
import com.permutassep.presentation.view.PostsListView;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class PagedPostListPresenter implements Presenter {

    private final UseCase getPagedPostsUseCase;
    private final PostPageModelDataMapper postPageModelDataMapper;
    private final PostModelDataMapper postModelDataMapper;
    private PostsListView postsListView;

    @Inject
    public PagedPostListPresenter(@Named("pagedPostList") UseCase getPostsList, PostPageModelDataMapper postPageModelDataMapper, PostModelDataMapper postModelDataMapper) {
        this.getPagedPostsUseCase = getPostsList;
        this.postPageModelDataMapper = postPageModelDataMapper;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(PostsListView postsListView) {
        this.postsListView = postsListView;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.getPostPage();
    }

    /**
     * Loads all users.
     */
    private void getPostPage() {
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
        this.getPagedPostsUseCase.execute(new PagedPostListSubscriber());
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

    private void showUsersCollectionInView(PostPage postPage) {
        final PostPageModel postPageModel = this.postPageModelDataMapper.transform(postPage);
        this.postsListView.renderPostList(postPageModel.getResults());
    }

    private final class PagedPostListSubscriber extends DefaultSubscriber<PostPage> {

        @Override
        public void onCompleted() {
            PagedPostListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PagedPostListPresenter.this.hideViewLoading();
            PagedPostListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PagedPostListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(PostPage postPage) {
            PagedPostListPresenter.this.showUsersCollectionInView(postPage);
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
        getPagedPostsUseCase.unsubscribe();
    }
}