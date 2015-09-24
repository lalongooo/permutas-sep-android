package com.permutassep.presentation.view;

import com.permutassep.presentation.model.PostModel;

import java.util.Collection;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of publications.
 */
public interface PostsListView extends LoadDataView {

    /**
     * Render a post list in the UI.
     *
     * @param postModelCollection The collection of {@link PostModel} that will be shown.
     */
    void renderPostList(Collection<PostModel> postModelCollection);

    /**
     * View a {@link PostModel} details.
     *
     * @param postModel The user that will be shown.
     */
    void viewPostDetail(PostModel postModel);
}