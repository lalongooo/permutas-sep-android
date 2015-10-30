package com.permutassep.presentation.interfaces;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.presentation.model.PostModel;

/**
 * Interface for listening post list item click events.
 */
public interface PostListListener {
    void onPostClicked(final PostModel postModel);
}
