package com.permutassep.domain.interactor;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.AuthenticationRepository;

import rx.Observable;

public class AuthenticateUser extends UseCase {

    private LoginDataWrapper loginDataWrapper;
    private  AuthenticationRepository authenticationRepository;

    public AuthenticateUser(LoginDataWrapper loginDataWrapper, AuthenticationRepository authenticationRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.loginDataWrapper = loginDataWrapper;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authenticationRepository.login(loginDataWrapper);
    }
}
