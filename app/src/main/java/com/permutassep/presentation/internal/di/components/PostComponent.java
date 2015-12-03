package com.permutassep.presentation.internal.di.components;

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.ui.ActivityCreatePost;
import com.permutassep.ui.FragmentMyPosts;
import com.permutassep.ui.FragmentPagedNewsFeed;
import com.permutassep.ui.FragmentPostDetail;
import com.permutassep.ui.FragmentResult;

import dagger.Component;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PostModule.class})
public interface PostComponent extends ActivityComponent {

    void inject(FragmentPostDetail mFragmentPostDetail);

    void inject(FragmentPagedNewsFeed mFragmentPagedNewsFeed);

    void inject(FragmentMyPosts fragmentPagedPostList);

    void inject(ActivityCreatePost activityWritePost);

    void inject(FragmentResult mFragmentResult);

}