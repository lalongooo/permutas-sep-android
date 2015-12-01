package com.permutassep.presentation.presenter;

import com.permutassep.domain.Post;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.view.SearchPostsResultsView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class SearchPostsResultsPresenter implements MyPresenter<SearchPostsResultsView> {

    private final UseCase searchPostsResultsUseCase;
    private final PostModelDataMapper postModelDataMapper;
    private SearchPostsResultsView searchPostsResultsView;

    @Inject
    public SearchPostsResultsPresenter(@Named("searchPosts") UseCase getPostsList, PostModelDataMapper postModelDataMapper) {
        this.searchPostsResultsUseCase = getPostsList;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(SearchPostsResultsView searchPostsResultsView) {
        this.searchPostsResultsView = searchPostsResultsView;
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
        this.performPostsSearch();
    }

    public void onPostClicked(PostModel postModel) {
        this.searchPostsResultsView.viewPostDetail(postModel);
    }

    private void hideViewRetry() {
        this.searchPostsResultsView.hideRetry();
    }

    private void showViewLoading() {
        this.searchPostsResultsView.showLoading();
    }

    private void performPostsSearch() {
        this.searchPostsResultsUseCase.execute(new SearchPostsSubscriber());
    }

    private void hideViewLoading() {
        this.searchPostsResultsView.hideLoading();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.searchPostsResultsView.getContext(), errorBundle.getException());
        this.searchPostsResultsView.showError(errorMessage);
    }

    private void showViewRetry() {
        this.searchPostsResultsView.showRetry();
    }

    private void showUsersCollectionInView(Collection<Post> postCollection) {
        final Collection<PostModel> postModelCollection = this.postModelDataMapper.transform(postCollection);

        if (postModelCollection.isEmpty()) {
            this.searchPostsResultsView.showEmptyResultsMessage();
        } else {
            this.searchPostsResultsView.renderPostList(postModelCollection);
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
        searchPostsResultsUseCase.unsubscribe();
    }

    private final class SearchPostsSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            SearchPostsResultsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            SearchPostsResultsPresenter.this.hideViewLoading();
            SearchPostsResultsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SearchPostsResultsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Post> posts) {
            SearchPostsResultsPresenter.this.showUsersCollectionInView(posts);
        }
    }
}