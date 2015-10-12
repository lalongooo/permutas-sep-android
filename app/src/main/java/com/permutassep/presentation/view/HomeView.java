package com.permutassep.presentation.view;

import android.view.Menu;

/**
 * Created by lalongooo on 12/10/15.
 */
public interface HomeView {

    /**
     * Render the options for the menu in the MainActivity
     * @param menu The menu to draw the options for.
     */
    void renderToolbarOptions(Menu menu);

    /**
     * Render the NavigationDrawer options
     */
    void renderDrawerOptions();

    /**
     * Handles the selections in the NavigationDrawer
     */
    void onDrawerItemSelected(int drawerItemId);

    /**
     * Handles the selections in the MainActivity menu
     */
    void onMenuOptionItemSelected(int menuItemId);

}