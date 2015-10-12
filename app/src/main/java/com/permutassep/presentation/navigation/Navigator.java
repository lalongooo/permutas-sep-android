/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.permutassep.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.view.activity.BaseActivity;
import com.permutassep.presentation.view.activity.PostDetailsActivity;
import com.permutassep.presentation.view.fragment.BaseFragment;
import com.permutassep.presentation.view.fragment.PostDetailsFragment;
import com.permutassep.presentation.view.fragment.PostListFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    /**
     * Empty constructor used by Dagger 2
     */
    @Inject
    public void Navigator() {
    }

    /**
     * Goes to the post details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPostDetails(Context context, int postId) {
        if (context != null) {
            Intent intentToLaunch = PostDetailsActivity.getCallingIntent(context, postId);
            context.startActivity(intentToLaunch);
        }
    }


    /**
     * Navigates to the post list fragment.
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToPostList(BaseActivity activity) {
        if (activity != null) {
            replaceFragment(activity, R.id.fragmentContainer, PostListFragment.newInstance(), false);
        }
    }

    /**
     * Navigates to the post details fragment.
     *
     * @param activity An activity needed to open the destination fragment.
     */
    public void navigateToPostDetails(BaseActivity activity, int postId) {
        if (activity != null) {
            replaceFragment(activity, R.id.fragmentContainer, PostDetailsFragment.newInstance(postId), true);
        }
    }

    /**
     * Helper method to replace fragments in the host activity.
     *
     * @param activity        The fragment host activity.
     * @param containerViewId The container id of the fragment to be replaced.
     * @param fragment        The new fragment.
     * @param addToBackStack  Flag indicating if the replaced fragment will be added to the back stack.
     */
    protected void replaceFragment(BaseActivity activity, int containerViewId, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getFragmentTag());
        }
        fragmentTransaction.commit();
    }
}