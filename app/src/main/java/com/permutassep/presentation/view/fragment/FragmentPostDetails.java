package com.permutassep.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lalongooo.permutassep.R;
import com.permutassep.model.State;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PostDetailsPresenter;
import com.permutassep.presentation.utils.Utils;
import com.permutassep.presentation.view.PostDetailsView;
import com.permutassep.presentation.view.activity.BaseActivity;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lalongooo on 27/09/15.
 **/

public class FragmentPostDetails extends BaseFragment
        implements PostDetailsView, OnMapReadyCallback {

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
    @Bind(R.id.layoutPostDetails)
    LinearLayout layoutPostDetails;
    @Bind(R.id.mapView)
    MapView mapView;

    @Inject
    PostDetailsPresenter postDetailsPresenter;
    private int postId;
    private PostModel postModel;
    private PostComponent postComponent;

    /**
     * Empty constructor
     */
    public FragmentPostDetails() {
        super();
    }

    /**
     * A static method to create a new instance of the {@link FragmentPostDetails} class
     *
     * @param postId The id of the post to be displayed
     * @return An instance of {@link FragmentPostDetails}
     */
    public static FragmentPostDetails newInstance(int postId) {
        FragmentPostDetails fragmentPostDetails = new FragmentPostDetails();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POST_ID, postId);
        fragmentPostDetails.setArguments(args);

        return fragmentPostDetails;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_post_details, container, false);
        ButterKnife.bind(this, fragmentView);
        mapView.onCreate(savedInstanceState);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void initialize() {
        this.postId = getArguments().getInt(ARGUMENT_POST_ID);
        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .postModule(new PostModule(postId))
                .build();
        this.postComponent.inject(this);
        this.postDetailsPresenter.setView(this);
        this.postDetailsPresenter.initialize(this.postId);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        this.postDetailsPresenter.initialize(this.postId);
    }

    /**
     * Methods from the implemented interface PostDetailsView
     */

    @Override
    public void renderPost(PostModel post) {
        if (post != null) {
            HashMap<String, State> states = Utils.getStates(getActivity());

            this.postModel = post;
            this.mapView.getMapAsync(this);

            this.tvStateFromCode.setText(states.get((post.getStateFromCode())).getShortCode());
            this.tvStateToCode.setText(states.get((post.getStateToCode())).getShortCode());
            this.tvUserName.setText(post.getUser().getName());
            this.tvPostUserEmail.setText(post.getUser().getEmail());
            this.tvPostUserPhone.setText(post.getUser().getPhone());
            this.tvPostText.setText(post.getPostText());
            this.tvStateFrom.setText(post.getStateFrom());
            this.tvCityFrom.setText(post.getCityFrom());
            this.tvTownFrom.setText(post.getTownFrom());
            this.tvStateTo.setText(post.getStateTo());
            this.tvCityTo.setText(post.getCityTo());
            this.tvTownTo.setText(post.getTownTo());
            this.tvAcademicLevel.setText(post.getAcademicLevel());
            this.tvWorkdayType.setText(post.getWorkdayType());
            this.tvPositionType.setText(post.getPositionType());
            this.tvPostDate.setText(Utils.formatDate(post.getPostDate()));
            this.layoutPostDetails.setVisibility(View.VISIBLE);

            if (post.isTeachingCareer()) {
                tvIsTeachingCareer.setText(getString(R.string.app_post_detail_teaching_career));
            } else {
                tvIsTeachingCareer.setText(getString(R.string.app_post_detail_no_teaching_career));
            }
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

    /**
     * Synchronize with the fragment lifecycle by calling
     * the corresponding presenter methods
     */

    @Override
    public void onResume() {
        super.onResume();
        this.postDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postDetailsPresenter.pause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postDetailsPresenter.destroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * Methods from the implemented interface OnMapReadyCallback
     */

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng origin = getLatLng(postModel.getLatFrom(), postModel.getLonFrom());
        LatLng target = getLatLng(postModel.getLatTo(), postModel.getLonTo());

        PolylineOptions rectOptions = new PolylineOptions().add(origin).add(target);
        map.addPolyline(rectOptions);

        map.addMarker(new MarkerOptions().position(origin).title(getString(R.string.search_fragment_origin_label)));
        map.addMarker(new MarkerOptions().position(target).title(getString(R.string.search_fragment_target_label)));

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);


        //Calculate the markers to get their position
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        b.include(origin);
        b.include(target);

        LatLngBounds bounds = b.build();
        //Change the padding as per needed

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, mapView.getWidth(), mapView.getHeight(), 10);
        map.animateCamera(cu);
    }

    private LatLng getLatLng(String lat, String lng) {
        LatLng ll = null;

        if (lat.length() == 6 && lng.length() == 7) {
            double hoursLat = Double.valueOf(lat.substring(0, 2));
            double minLat = Double.valueOf(lat.substring(2, 4));
            double secLat = Double.valueOf(lat.substring(4, 6));

            double hoursLng = Double.valueOf(lng.substring(0, 3));
            double minLng = Double.valueOf(lng.substring(3, 5));
            double secLng = Double.valueOf(lng.substring(5, 7));

            double decimalLat = Math.signum(hoursLat) * (Math.abs(hoursLat) + (minLat / 60.0) + (secLat / 3600.0));
            double decimalLng = Math.signum(hoursLng) * (Math.abs(hoursLng) + (minLng / 60.0) + (secLng / 3600.0));

            ll = new LatLng(decimalLat, -decimalLng);
        }

        return ll;
    }
}