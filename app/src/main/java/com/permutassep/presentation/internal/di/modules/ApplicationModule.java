package com.permutassep.presentation.internal.di.modules;

import android.content.Context;

import com.permutassep.data.executor.JobExecutor;
import com.permutassep.data.repository.PostDataRepository;
import com.permutassep.data.repository.UserDataRepository;
import com.permutassep.domain.executor.PostExecutionThread;
import com.permutassep.domain.executor.ThreadExecutor;
import com.permutassep.domain.repository.PostRepository;
import com.permutassep.domain.repository.UserRepository;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.UIThread;
import com.permutassep.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    PostRepository providePostRepository(PostDataRepository postDataRepository) {
        return postDataRepository;
    }
}
