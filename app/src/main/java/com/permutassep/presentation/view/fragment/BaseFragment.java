package com.permutassep.presentation.view.fragment;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.permutassep.presentation.internal.di.HasComponent;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public class BaseFragment extends Fragment {

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
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}