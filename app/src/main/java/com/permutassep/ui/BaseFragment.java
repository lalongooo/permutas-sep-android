package com.permutassep.ui;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.navigation.Navigator;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public class BaseFragment extends Fragment {

    protected Navigator.NavigationListener navigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.navigationListener = (Navigator.NavigationListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext()) == 0) {
            // Get tracker.
            Tracker t = ((AndroidApplication) getActivity().getApplication()).getTracker();
            // Set screen name.
            t.setScreenName(getClass().getName());
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to hide the keyboard on fragment navigation
     */
    protected void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = getActivity().getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}