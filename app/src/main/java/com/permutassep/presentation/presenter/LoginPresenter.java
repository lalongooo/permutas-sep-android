package com.permutassep.presentation.presenter;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.support.annotation.NonNull;

import com.permutassep.domain.User;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.ResetPassword;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.mapper.UserModelDataMapper;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.view.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class LoginPresenter implements Presenter {

    private final UseCase authenticateUserUseCase;
    private final ResetPassword resetPasswordUseCase;
    private UserModelDataMapper userModelDataMapper;
    private LoginView loginView;

    @Inject
    public LoginPresenter(@Named("authenticateUser") UseCase authenticateUserUseCase, ResetPassword resetPasswordUseCase, UserModelDataMapper userModelDataMapper) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void login() {
        this.hideViewRetry();
        this.showViewLoading();
        this.executeLogin();
    }

    public void validateEmail(String email) {
        this.hideViewRetry();
        this.showViewLoadingValidateUser();
        this.executeValidateEmail(email);
    }

    public void resetPassword(String email) {
        this.hideViewRetry();
        this.showViewLoadingValidateUser();
        this.resetPasswordUseCase.setEmail(email);
        this.resetPasswordUseCase.execute(new ResetPasswordSubscriber(email));
    }

    private void hideViewRetry() {
        this.loginView.hideRetry();
    }

    private void showViewLoading() {
        this.loginView.showLoading();
    }

    private void executeLogin() {
        this.authenticateUserUseCase.execute(new LoginSubscriber());
    }

    private void showViewLoadingValidateUser() {
        this.loginView.showLoadingValidateUser();
    }

    private void executeValidateEmail(String email) {
        this.authenticateUserUseCase.execute(new ValidateEmailSubscriber(email));
    }

    private void hideViewLoading() {
        this.loginView.hideLoading();
    }

    private void showViewRetry() {
        this.loginView.showRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.loginView.getContext(), errorBundle.getException());
        this.loginView.showError(errorMessage);
    }

    public void setView(@NonNull LoginView loginView) {
        this.loginView = loginView;
    }

    private void authorizeUser(User user) {
        final UserModel userModel = this.userModelDataMapper.transform(user);
        this.loginView.authorizeUser(userModel);
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
        authenticateUserUseCase.unsubscribe();
        resetPasswordUseCase.unsubscribe();
    }

    private final class LoginSubscriber extends DefaultSubscriber<User> {
        @Override
        public void onCompleted() {
            LoginPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            showViewRetry();
        }

        @Override
        public void onNext(User user) {
            LoginPresenter.this.authorizeUser(user);
        }
    }

    private final class ValidateEmailSubscriber extends DefaultSubscriber<User> {

        private String email;

        public ValidateEmailSubscriber(String email) {
            this.email = email;
        }

        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            loginView.performPasswordReset(email);
        }

        @Override
        public void onNext(User user) {
            loginView.notRegisteredUser();
        }
    }

    private final class ResetPasswordSubscriber extends DefaultSubscriber<String> {

        private String email;

        public ResetPasswordSubscriber(String email) {
            this.email = email;
        }

        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            loginView.notRegisteredUser();
        }

        @Override
        public void onNext(String string) {
            loginView.passwordResetRequestSent(email);
        }

    }
}