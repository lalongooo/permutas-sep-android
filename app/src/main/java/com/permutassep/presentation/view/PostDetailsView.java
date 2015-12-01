package com.permutassep.presentation.view;

import com.permutassep.presentation.model.PostModel;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */


/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a publication detail
 */
public interface PostDetailsView extends LoadDataView {
    void renderPost(PostModel postModel);
}
