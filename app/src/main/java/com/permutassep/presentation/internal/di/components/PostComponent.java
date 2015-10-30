package com.permutassep.presentation.internal.di.components;

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.view.fragment.FragmentMyPostList;
import com.permutassep.presentation.view.fragment.FragmentPagedPostList;
import com.permutassep.presentation.view.fragment.FragmentPostDetails;
import com.permutassep.presentation.view.fragment.FragmentPostList;

import dagger.Component;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PostModule.class})
public interface PostComponent extends ActivityComponent {

    void inject(FragmentPostList fragmentPostList);

    void inject(FragmentPostDetails fragmentPostDetails);

    void inject(FragmentPagedPostList fragmentPagedPostList);

    void inject(FragmentMyPostList fragmentPagedPostList);
}