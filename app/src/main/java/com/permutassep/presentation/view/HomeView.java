package com.permutassep.presentation.view;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.view.Menu;

public interface HomeView {

    /**
     * Render the NavigationDrawer options
     */
    void renderDrawerOptions();

    /**
     * Handles the selections in the NavigationDrawer
     */
    void onDrawerItemSelected(int drawerItemId);
}