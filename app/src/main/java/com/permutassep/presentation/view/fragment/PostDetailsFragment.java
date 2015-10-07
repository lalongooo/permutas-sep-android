package com.permutassep.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PostDetailsPresenter;
import com.permutassep.presentation.view.PostDetailsView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lalongooo on 27/09/15.
 **/

public class PostDetailsFragment extends BaseFragment implements PostDetailsView {

    private static final String ARGUMENT_POST_ID = "ARGUMENT_POST_ID";

    /**
     * UI elements
     */

    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvPostUserEmail)
    TextView tvPostUserEmail;
    @Bind(R.id.tvPostUserPhone)
    TextView tvPostUserPhone;
    @Bind(R.id.tvPostText)
    TextView tvPostText;
    @Bind(R.id.tvStateFromCode)
    TextView tvStateFromCode;
    @Bind(R.id.tvStateToCode)
    TextView tvStateToCode;
    @Bind(R.id.tvStateFrom)
    TextView tvStateFrom;
    @Bind(R.id.tvCityFrom)
    TextView tvCityFrom;
    @Bind(R.id.tvTownFrom)
    TextView tvTownFrom;
    @Bind(R.id.tvStateTo)
    TextView tvStateTo;
    @Bind(R.id.tvCityTo)
    TextView tvCityTo;
    @Bind(R.id.tvTownTo)
    TextView tvTownTo;
    @Bind(R.id.tvAcademicLevel)
    TextView tvAcademicLevel;
    @Bind(R.id.tvWorkdayType)
    TextView tvWorkdayType;
    @Bind(R.id.tvPositionType)
    TextView tvPositionType;
    @Bind(R.id.tvIsTeachingCareer)
    TextView tvIsTeachingCareer;
    @Bind(R.id.tvPostDate)
    TextView tvPostDate;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;

    @Inject
    PostDetailsPresenter postDetailsPresenter;
    private int postId;

    /**
     * Empty constructor
     */
    public PostDetailsFragment() {
        super();
    }

    /**
     * A static method to create a new instance of the {@link PostDetailsFragment} class
     *
     * @param postId The id of the post to be displayed
     * @return An instance of {@link PostDetailsFragment}
     */
    public static PostDetailsFragment newInstance(int postId) {
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POST_ID, postId);
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
        this.postDetailsPresenter.setView(this);
        this.postId = getArguments().getInt(ARGUMENT_POST_ID);
        this.postDetailsPresenter.initialize(this.postId);
    }

    /**
     * Methods from the implemented interface PostDetailsView
     */

    @Override
    public void renderPost(PostModel post) {
        if (post != null) {

            this.tvUserName.setText(post.getUser().getName());
            this.tvPostUserEmail.setText(post.getUser().getEmail());
            this.tvPostUserPhone.setText(post.getUser().getPhone());
            this.tvPostText.setText(post.getPostText());
//            this.tvStateFromCode.setText();
//            this.tvStateToCode.setText();
//            this.tvStateFrom.setText();
//            this.tvCityFrom.setText();
//            this.tvTownFrom.setText();
//            this.tvStateTo.setText();
//            this.tvCityTo.setText();
//            this.tvTownTo.setText();
//            this.tvAcademicLevel.setText();
//            this.tvWorkdayType.setText();
//            this.tvPositionType.setText();
//            this.tvIsTeachingCareer.setText();
//            this.tvPostDate.setText();

        }
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }
}