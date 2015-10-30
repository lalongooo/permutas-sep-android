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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.view.activity.BaseActivity;
import com.permutassep.presentation.view.fragment.FragmentCompleteFbData;
import com.permutassep.presentation.view.fragment.FragmentLogin;
import com.permutassep.presentation.view.fragment.FragmentLoginSignUp;
import com.permutassep.presentation.view.fragment.FragmentMyPostList;
import com.permutassep.presentation.view.fragment.FragmentPagedPostList;
import com.permutassep.presentation.view.fragment.FragmentPostDetails;
import com.permutassep.presentation.view.fragment.FragmentSignUp;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    public interface NavigationListener {
        void onNextFragment(Class c);
    }

    /**
     * Empty constructor used by Dagger 2
     */
    @Inject
    public Navigator() {
    }

    /**
     * Navigates to the log in/sign up screen fragment
     *
     * @param activity An activity needed to load the destination fragment.
     */
    public void navigateToLoginSignUp(BaseActivity activity, boolean addToBackStack) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, FragmentLoginSignUp.newInstance());
            if(addToBackStack){
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
            if(fragment != null){
                fragmentTransaction.hide(fragment);
            }
            if(addToBackStack){
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentPagedPostList.newInstance());
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
            fragmentTransaction.add(R.id.fragmentContainer, FragmentPostDetails.newInstance(postId));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }

    /**
     * Navigates to the posts list owned by the logged user.
     * @param activity An activity needed to load the destination fragment.
     * @param userId The user id
     */
    public void navigateToUserPostList(BaseActivity activity, int userId, boolean addToBackStack) {
        if (activity != null) {

            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(fragment != null){
                fragmentTransaction.hide(fragment);
            }
            if(addToBackStack){
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.add(R.id.fragmentContainer, FragmentMyPostList.newInstance(userId));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }
}