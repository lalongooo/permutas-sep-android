package com.permutassep.domain.interactor;

import com.permutassep.domain.ConfirmPasswordResetDataWrapper;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PasswordResetRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class ConfirmPasswordReset extends UseCase {

    private ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper;
    private PasswordResetRepository passwordResetRepository;

    @Inject
    public ConfirmPasswordReset(PasswordResetRepository passwordResetRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.passwordResetRepository = passwordResetRepository;
    }

    public void setConfirmPasswordReset(ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper) {
        this.confirmPasswordResetDataWrapper = confirmPasswordResetDataWrapper;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return passwordResetRepository.confirmPasswordReset(confirmPasswordResetDataWrapper);
    }
}