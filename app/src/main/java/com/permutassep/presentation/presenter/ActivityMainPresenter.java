package com.permutassep.presentation.presenter;

import com.permutassep.domain.Post;
import com.permutassep.domain.PostPage;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.GetMyPostsList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class ActivityMainPresenter implements Presenter {

    private final GetMyPostsList getMyPostsListUseCase;

    @Inject
    public ActivityMainPresenter(@Named("myPostsList") GetMyPostsList getMyPostsListUseCase) {
        this.getMyPostsListUseCase = getMyPostsListUseCase;
    }

    public void syncPostsInterests(int userId) {
        this.getMyPostsListUseCase.setUserId(userId);
        this.getMyPostsListUseCase.execute(new SyncMyPostsSubscriber());

    }

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

    private final class SyncMyPostsSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Post> posts) {

        }
    }
}