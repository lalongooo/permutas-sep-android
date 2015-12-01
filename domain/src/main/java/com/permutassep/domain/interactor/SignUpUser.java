package com.permutassep.domain.interactor;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.User;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.AuthenticationRepository;

import rx.Observable;

public class SignUpUser extends UseCase {

    private User user;
    private AuthenticationRepository authenticationRepository;

    public SignUpUser(User user, AuthenticationRepository authenticationRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.user = user;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authenticationRepository.signUp(user);
    }
}
