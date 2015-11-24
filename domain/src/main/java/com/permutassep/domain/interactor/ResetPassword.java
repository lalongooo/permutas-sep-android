package com.permutassep.domain.interactor;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.Email;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.AuthenticationRepository;

import javax.inject.Inject;

import rx.Observable;

public class ResetPassword extends UseCase {

    private Email email;
    private AuthenticationRepository authenticationRepository;

    @Inject
    public ResetPassword(AuthenticationRepository authenticationRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authenticationRepository = authenticationRepository;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authenticationRepository.resetPassword(email);
    }
}
