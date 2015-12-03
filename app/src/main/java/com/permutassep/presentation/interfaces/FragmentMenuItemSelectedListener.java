package com.permutassep.presentation.interfaces;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.ui.ActivityMain;

/**
 * Interface implemented in the {@link ActivityMain},
 * it is used to handle the menu options item selected in different fragments.
 */
public interface FragmentMenuItemSelectedListener {
    void onMenuItemSelected(int menuId);
}
