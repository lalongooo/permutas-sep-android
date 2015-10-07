package com.permutassep.presentation.internal.di.components;

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.view.fragment.PostDetailsFragment;
import com.permutassep.presentation.view.fragment.PostListFragment;

import dagger.Component;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PostModule.class})
public interface PostComponent extends ActivityComponent {

    void inject(PostListFragment postListFragment);

    void inject(PostDetailsFragment postDetailsFragment);
}