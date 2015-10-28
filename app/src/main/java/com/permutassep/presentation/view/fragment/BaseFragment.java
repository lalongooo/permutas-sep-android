package com.permutassep.presentation.view.fragment;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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