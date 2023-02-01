package com.permutassep.presentation.presenter;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.Post;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.WritePost;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.mapper.PostModelDataMapper;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.view.WritePostView;

import javax.inject.Inject;

@PerActivity
public class WritePostPresenter implements Presenter {

    private final WritePost writePostUseCase;
    private final PostModelDataMapper postModelDataMapper;
    private WritePostView writePostView;
    private UserModel userModel;

    @Inject
    public WritePostPresenter(WritePost writePostUseCase, PostModelDataMapper postModelDataMapper) {
        this.writePostUseCase = writePostUseCase;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void writePost(PostModel postModel) {
        this.hideViewRetry();
        this.showViewLoading();
        this.sendPost(postModel);
    }

    private void hideViewRetry() {
        this.writePostView.hideRetry();
    }

    private void showViewLoading() {
        this.writePostView.showLoading();
    }

    private void sendPost(PostModel postModel) {
        this.writePostUseCase.setPost(postModelDataMapper.transform(postModel));
        this.writePostUseCase.execute(new WritePostSubscriber());
    }

    private void hideViewLoading() {
        this.writePostView.hideLoading();
    }

    private void showViewRetry() {
        this.writePostView.showRetry();
    }

    public void setView(WritePostView writePostView, UserModel userModel) {
        this.writePostView = writePostView;
        this.userModel = userModel;
    }

    private void writtenPost(Post post) {
        PostModel postModel = this.postModelDataMapper.transform(post);
        postModel.setUser(userModel);
        writePostView.writtenPost(postModel);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        writePostUseCase.unsubscribe();
    }

    private final class WritePostSubscriber extends DefaultSubscriber<Post> {
        @Override
        public void onCompleted() {
            WritePostPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            WritePostPresenter.this.hideViewLoading();
            WritePostPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Post post) {
            WritePostPresenter.this.writtenPost(post);
        }

    }
}
