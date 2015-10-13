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

import android.support.v4.app.FragmentTransaction;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.view.activity.BaseActivity;
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
     * Navigates to the post list fragment.
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToPostList(BaseActivity activity) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, PostListFragment.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the post details fragment.
     *
     * @param activity An activity needed to open the destination fragment.
     */
    public void navigateToPostDetails(BaseActivity activity, int postId) {
        if (activity != null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
            fragmentTransaction.add(R.id.fragmentContainer, PostDetailsFragment.newInstance(postId));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }
}