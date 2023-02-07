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

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lalongooo.permutassep.R;
import com.permutassep.ui.ActivityAppOverview;
import com.permutassep.ui.ActivityCreatePost;
import com.permutassep.ui.BaseActivity;
import com.permutassep.ui.FragmentCompleteFbData;
import com.permutassep.ui.FragmentLogin;
import com.permutassep.ui.FragmentLoginSignUp;
import com.permutassep.ui.FragmentMyPosts;
import com.permutassep.ui.FragmentPagedNewsFeed;
import com.permutassep.ui.FragmentPostDetail;
import com.permutassep.ui.FragmentResult;
import com.permutassep.ui.FragmentSearch;
import com.permutassep.ui.FragmentSignUp;

import java.util.HashMap;

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
    public Navigator() {
    }


    /**
     * Navigates to the start of the app (ActivityAppOverview) after the user logs off.
     *
     * @param activity An activity needed to open the destination fragment.
     */
    public void navigateToStart(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ActivityAppOverview.class));
        activity.finish();
    }

    /**
     * Navigates to the log in/sign up screen fragment
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToLoginSignUp(BaseActivity activity, boolean addToBackStack) {
        if (activity != null) {

            androidx.fragment.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, FragmentLoginSignUp.newInstance());
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the sign up screen fragment
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToSignUp(BaseActivity activity) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
            fragmentTransaction.add(R.id.fragmentContainer, FragmentSignUp.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the sign up screen fragment
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToCompleteFbData(BaseActivity activity, Bundle bundle) {
        if (activity != null && bundle != null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
            fragmentTransaction.add(R.id.fragmentContainer, FragmentCompleteFbData.newInstance(bundle));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the login screen fragment
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToLogin(BaseActivity activity) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
            fragmentTransaction.add(R.id.fragmentContainer, FragmentLogin.newInstance());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the post list fragment.
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToPostList(BaseActivity activity, boolean addToBackStack) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentPagedNewsFeed.newInstance());
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
        activity.getSupportFragmentManager()
                .beginTransaction()
                .hide(activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                .add(R.id.fragmentContainer, FragmentPostDetail.newInstance(postId))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    /**
     * Navigates to the post details fragment coming from a push notification.
     *
     * @param activity An activity needed to open the destination fragment.
     * @param postId   The id of the publication to be displayed on the fragment
     */
    public void navigateToPostDetailsFromNotification(BaseActivity activity, int postId) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, FragmentPostDetail.newInstance(postId))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    /**
     * Navigates to the posts list owned by the logged user.
     *
     * @param activity An activity needed to load the destination fragment.
     * @param userId   The user id
     */
    public void navigateToUserPostList(BaseActivity activity, int userId, boolean addToBackStack) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentMyPosts.newInstance(userId));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Goes to the user list screen.
     *
     * @param baseActivity A Context needed to open the destiny activity.
     */
    public void navigateToWritePost(BaseActivity baseActivity) {
        if (baseActivity != null) {
            Intent intentToLaunch = ActivityCreatePost.getCallingIntent(baseActivity);
            baseActivity.startActivityForResult(intentToLaunch, 8999);
        }
    }

    /**
     * Navigates to the fragment where the posts search filter are selected.
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToSearchPosts(BaseActivity activity, boolean addToBackStack) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentSearch.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the fragment where the search posts results are displayed.
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToSearchPostsResults(BaseActivity activity, boolean addToBackStack, HashMap<String, String> searchParams) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentResult.newInstance(searchParams));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    public interface NavigationListener {
        void onNextFragment(Class c);
    }
}