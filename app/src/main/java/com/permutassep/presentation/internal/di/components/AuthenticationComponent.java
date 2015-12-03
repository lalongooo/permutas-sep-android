package com.permutassep.presentation.internal.di.components;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.internal.di.modules.AuthenticationModule;
import com.permutassep.ui.FragmentCompleteFbData;
import com.permutassep.ui.FragmentLogin;
import com.permutassep.ui.FragmentNewPassword;
import com.permutassep.ui.FragmentSignUp;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AuthenticationModule.class})
public interface AuthenticationComponent extends ActivityComponent {

    void inject(FragmentLogin fragmentLogin);

    void inject(FragmentSignUp fragmentSignUp);

    void inject(FragmentCompleteFbData fragmentCompleteFbData);

    void inject(FragmentNewPassword fragmentNewPassword);
}