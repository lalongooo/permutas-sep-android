package com.permutassep.domain.interactor;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.Email;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PasswordResetRepository;

import javax.inject.Inject;

import rx.Observable;

public class ResetPassword extends UseCase {

    private Email email;
    private PasswordResetRepository passwordResetRepository;

    @Inject
    public ResetPassword(PasswordResetRepository passwordResetRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.passwordResetRepository = passwordResetRepository;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return passwordResetRepository.resetPassword(email);
    }
}
