package com.permutassep.presentation.presenter;

import com.lalongooo.permutassep.R;
import com.permutassep.domain.exception.DefaultErrorBundle;
import com.permutassep.domain.exception.ErrorBundle;
import com.permutassep.domain.interactor.ConfirmPasswordReset;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.presentation.exception.ErrorMessageFactory;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.mapper.ConfirmPasswordResetDataMapper;
import com.permutassep.presentation.model.ConfirmPasswordResetDataWrapperModel;
import com.permutassep.presentation.view.NewPasswordView;

import javax.inject.Inject;

@PerActivity
public class NewPasswordPresenter implements Presenter {

    private final ConfirmPasswordReset confirmPasswordResetUseCase;
    private NewPasswordView newPasswordView;
    private ConfirmPasswordResetDataMapper confirmPasswordResetDataMapper;

    @Inject
    public NewPasswordPresenter(ConfirmPasswordReset confirmPasswordResetUseCase, ConfirmPasswordResetDataMapper confirmPasswordResetDataMapper) {
        this.confirmPasswordResetUseCase = confirmPasswordResetUseCase;
        this.confirmPasswordResetDataMapper = confirmPasswordResetDataMapper;
    }

    public void setView(NewPasswordView newPasswordView) {
        this.newPasswordView = newPasswordView;
    }

    public void confirmPasswordReset(ConfirmPasswordResetDataWrapperModel confirmPasswordResetDataWrapperModel) {
        this.newPasswordView.hideRetry();
        this.newPasswordView.showLoading();

        this.executePasswordReset(confirmPasswordResetDataWrapperModel);
    }

    private void executePasswordReset(ConfirmPasswordResetDataWrapperModel confirmPasswordResetDataWrapperModel) {
        this.confirmPasswordResetUseCase.setConfirmPasswordReset(confirmPasswordResetDataMapper.transform(confirmPasswordResetDataWrapperModel));
        this.confirmPasswordResetUseCase.execute(new PasswordResetSubscriber());
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.newPasswordView.getContext(), errorBundle.getException());

        if (errorMessage.equals(this.newPasswordView.getContext().getString(R.string.exception_message_generic))) {
            errorMessage = this.newPasswordView.getContext().getString(R.string.password_reset_obsolete_token_error);
        }
        this.newPasswordView.showError(errorMessage);
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

    private final class PasswordResetSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onCompleted() {
            newPasswordView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            newPasswordView.hideLoading();
            newPasswordView.showRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(String s) {
            newPasswordView.passwordCorrectlyReset();
        }
    }
}