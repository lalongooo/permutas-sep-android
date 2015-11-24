package com.permutassep.presentation.internal.di.modules;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.User;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.interactor.AuthenticateUser;
import com.permutassep.domain.interactor.ResetPassword;
import com.permutassep.domain.interactor.SignUpUser;
import com.permutassep.domain.interactor.UseCase;
import com.permutassep.domain.repository.AuthenticationRepository;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.model.UserModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {

    private String email;
    private String password;
    private UserModel userModel;

    public AuthenticationModule(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationModule(UserModel userModel) {
        this.userModel = userModel;
    }

    @Provides
    @PerActivity
    @Named("signUpUser")
    UseCase providesSignUpUserUseCase(
            AuthenticationRepository authenticationRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        User user = new User();
        user.setName(this.userModel.getName());
        user.setEmail(this.userModel.getEmail());
        user.setPhone(this.userModel.getPhone());
        user.setPassword(this.userModel.getPassword());
        user.setSocialUserId(this.userModel.getSocialUserId());
        user.setId(this.userModel.getId());
        return new SignUpUser(user, authenticationRepository, threadExecutor, postExecutionThread);
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

    @Provides
    @PerActivity
    @Named("resetPassword")
    ResetPassword providesResetPasswordUseCase(
            AuthenticationRepository authenticationRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new ResetPassword(authenticationRepository, threadExecutor, postExecutionThread);
    }
}