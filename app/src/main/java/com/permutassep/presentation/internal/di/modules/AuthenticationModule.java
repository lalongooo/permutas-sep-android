package com.permutassep.presentation.internal.di.modules;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.interactor.AuthenticateUser;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.domain.repository.AuthenticationRepository;
import com.permutassep.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {

    private String email;
    private String password;

    public AuthenticationModule(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Provides
    @PerActivity
    @Named("authenticateUser")
    UseCase providesAuthenticateUserUseCase(
            AuthenticationRepository authenticationRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new AuthenticateUser(new LoginDataWrapper(email, password), authenticationRepository, threadExecutor, postExecutionThread);
    }
}