package com.permutassep.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PostDetailsPresenter;
import com.permutassep.presentation.view.PostDetailsView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by lalongooo on 27/09/15.
 **/

public class PostDetailsFragment extends BaseFragment implements PostDetailsView {

    private static final String ARGUMENT_KEY_USER_ID = "ARGUMENT_POST_ID";

    @Inject
    private PostDetailsPresenter postDetailsPresenter;

    /**
     * Empty constructor
     */
    public PostDetailsFragment() {
        super();
    }

    /**
     * A static method to create a new instance of the {@link PostDetailsFragment} class
     * @param postId The id of the post to be displayed
     * @return An instance of {@link PostDetailsFragment}
     */
    public static PostDetailsFragment newInstance(int postId){
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_KEY_USER_ID, postId);
        postDetailsFragment.setArguments(args);

        return postDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_post_details, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void initialize() {
        this.getComponent(PostComponent.class).inject(this);
        this.userDetailsPresenter.setView(this);
        this.userId = getArguments().getInt(ARGUMENT_KEY_USER_ID);
        this.userDetailsPresenter.initialize(this.userId);
    }

    @Override
    public void renderPost(PostModel postModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}