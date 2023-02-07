package com.permutassep.presentation.presenter;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.User;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.mapper.UserModelDataMapper;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.view.SignUpView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class SignUpPresenter implements Presenter {

    private final UseCase signUpUserUseCase;
    private UserModelDataMapper userModelDataMapper;
    private SignUpView signUpView;

    @Inject
    public SignUpPresenter(@Named("signUpUser") UseCase signUpUserUseCase, UserModelDataMapper userModelDataMapper) {
        this.signUpUserUseCase = signUpUserUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void signUp() {
        this.hideViewRetry();
        this.showViewLoading();
        this.signUpUser();
    }

    private void hideViewRetry() {
        this.signUpView.hideRetry();
    }

    private void showViewLoading() {
        this.signUpView.showLoading();
    }

    private void signUpUser() {
        this.signUpUserUseCase.execute(new SignUpSubscriber());
    }

    private void hideViewLoading() {
        this.signUpView.hideLoading();
    }

    private void showViewRetry() {
        this.signUpView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.signUpView.getContext(), errorBundle.getException());
        if(errorBundle.getErrorMessage().contains("400")){
            this.signUpView.showDuplicateEmailErrorMessage();
        }else{
            this.signUpView.showError(errorMessage);
        }
    }

    public void setView(SignUpView loginView) {
        this.signUpView = loginView;
    }

    private void signedUpUser(User user) {
        final UserModel userModel = this.userModelDataMapper.transform(user);
        this.signUpView.signedUpUser(userModel);
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
        signUpUserUseCase.unsubscribe();
    }

    /**
     * Methods from the DefaultSubscriber<Post>
     */
    private final class SignUpSubscriber extends DefaultSubscriber<User> {
        @Override
        public void onCompleted() {
            SignUpPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            SignUpPresenter.this.hideViewLoading();
            SignUpPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            SignUpPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(User user) {
            SignUpPresenter.this.signedUpUser(user);
        }

    }
}
