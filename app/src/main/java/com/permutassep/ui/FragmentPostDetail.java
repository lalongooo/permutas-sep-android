package com.permutassep.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentPostDetailsBinding;
import com.permutassep.model.State;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.navigation.Navigator;
import com.permutassep.presentation.presenter.PostDetailsPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.utils.Utils;
import com.permutassep.presentation.view.PostDetailsView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.inject.Inject;

public class FragmentPostDetail extends BaseFragment
        implements PostDetailsView /* OnMapReadyCallback */ {

    private static final String ARGUMENT_POST_ID = "ARGUMENT_POST_ID";
    private static final String MAILTO_SCHEMA = "mailto";

    private CaFragmentPostDetailsBinding binding;

    @Inject
    PostDetailsPresenter postDetailsPresenter;
    private int postId;
    private PostModel postModel;
    private PostComponent postComponent;

    /**
     * A static method to create a new instance of the {@link FragmentPostDetail} class
     *
     * @param postId The id of the post to be displayed
     * @return An instance of {@link FragmentPostDetail}
     */
    public static FragmentPostDetail newInstance(int postId) {
        FragmentPostDetail mFragmentPostDetail = new FragmentPostDetail();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POST_ID, postId);
        mFragmentPostDetail.setArguments(args);

        return mFragmentPostDetail;
    }

    protected Navigator.NavigationListener navigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.navigationListener = (Navigator.NavigationListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentPostDetailsBinding.inflate(inflater, container, false);
//        mapView.onCreate(savedInstanceState);
//        ivArrow.setImageDrawable(
//                new IconicsDrawable(getActivity(), FontAwesome.Icon.faw_angle_right)
//                        .color(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
//                        .sizeDp(30));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initialize();

        binding.viewRetry.btRetry.setOnClickListener(v -> postDetailsPresenter.initialize(postId));

        binding.postDetails.btnCall.setOnClickListener(v -> {
            if (!PrefUtils.isLoggedUser(requireActivity())) {
                showSignUpInvitation();
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:".concat(postModel.getUser().getPhone())));
                startActivity(callIntent);
            }
        });

        binding.postDetails.btnEmail.setOnClickListener(v -> {
            if (!PrefUtils.isLoggedUser(requireActivity())) {
                showSignUpInvitation();
            } else {
                UserModel user = PrefUtils.getUser(requireActivity());
                String emailContent = String.format(getString(R.string.app_post_detail_mail_intent_content), postModel.getUser().getName(), user.getPhone(), user.getEmail(), user.getName());
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(MAILTO_SCHEMA, postModel.getUser().getEmail(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_post_detail_mail_intent_subject));
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
                startActivity(Intent.createChooser(emailIntent, getString(R.string.app_post_detail_mail_intent_chooser_title)));
            }
        });
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

    /**
     * Methods from the implemented interface PostDetailsView
     */

    @Override
    public void renderPost(PostModel post) {
        if (post != null) {
            HashMap<String, State> states = Utils.getStates(requireActivity());

            this.postModel = post;
            // this.mapView.getMapAsync(this);
            binding.postDetails.tvStateFromCode.setText(states.get((post.getStateFromCode())).getShortCode());
            binding.postDetails.tvStateToCode.setText(states.get((post.getStateToCode())).getShortCode());
            binding.postDetails.tvUserName.setText(post.getUser().getName());
            binding.postDetails.tvPostUserEmail.setText(PrefUtils.isLoggedUser(requireActivity()) ? post.getUser().getEmail() : post.getUser().getEmail().replace(post.getUser().getEmail().substring(0, post.getUser().getEmail().indexOf("@")), new String(new char[post.getUser().getEmail().substring(0, post.getUser().getEmail().indexOf("@")).length()]).replace("\0", "*")));
            binding.postDetails.tvPostUserPhone.setText(PrefUtils.isLoggedUser(requireActivity()) ? post.getUser().getPhone() : post.getUser().getPhone().replace(post.getUser().getPhone().substring(0, Config.HIDE_PHONE_CHARACTERS), new String(new char[6]).replace("\0", Config.CHARACTER_FOR_ANON_USER)));
            binding.postDetails.tvPostText.setText(post.getPostText());
            binding.postDetails.tvStateFrom.setText(post.getStateFrom());
            binding.postDetails.tvCityFrom.setText(post.getCityFrom());
            binding.postDetails.tvTownFrom.setText(post.getTownFrom());
            binding.postDetails.tvStateTo.setText(post.getStateTo());
            binding.postDetails.tvCityTo.setText(post.getCityTo());
            binding.postDetails.tvTownTo.setText(post.getTownTo());
            binding.postDetails.tvAcademicLevel.setText(post.getAcademicLevel());
            binding.postDetails.tvWorkdayType.setText(post.getWorkdayType());
            binding.postDetails.tvPositionType.setText(post.getPositionType());
            binding.postDetails.tvPostDate.setText(Utils.formatDate(post.getPostDate()));
            binding.postDetails.layoutPostDetails.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(post.getUser().getProfilePictureUrl())
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(getActivity())
                    .into(binding.postDetails.imageView);

            if (post.isTeachingCareer()) {
                binding.postDetails.tvIsTeachingCareer.setText(getString(R.string.app_post_detail_teaching_career));
            } else {
                binding.postDetails.tvIsTeachingCareer.setText(getString(R.string.app_post_detail_no_teaching_career));
            }
        }
    }

    @Override
    public void showLoading() {
        binding.progressView.rlProgress.setVisibility(View.VISIBLE);
        requireActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        binding.progressView.rlProgress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        binding.viewRetry.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        binding.viewRetry.rlRetry.setVisibility(View.GONE);
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
//        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postDetailsPresenter.pause();
//        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postDetailsPresenter.destroy();
//        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    //    @Override
//    public void onMapReady(GoogleMap map) {
//
//        LatLng origin = getLatLng(postModel.getLatFrom(), postModel.getLonFrom());
//        LatLng target = getLatLng(postModel.getLatTo(), postModel.getLonTo());
//
//        PolylineOptions rectOptions = new PolylineOptions().add(origin).add(target);
//        map.addPolyline(rectOptions);
//
//        map.addMarker(new MarkerOptions().position(origin).title(getString(R.string.search_fragment_origin_label)));
//        map.addMarker(new MarkerOptions().position(target).title(getString(R.string.search_fragment_target_label)));
//
//        map.getUiSettings().setZoomControlsEnabled(true);
//        map.getUiSettings().setAllGesturesEnabled(true);
//        map.getUiSettings().setZoomGesturesEnabled(true);
//
//
//        //Calculate the markers to get their position
//        LatLngBounds.Builder b = new LatLngBounds.Builder();
//        b.include(origin);
//        b.include(target);
//
//        LatLngBounds bounds = b.build();
//
//        try {
//            int paddingInPixels = (int)(Math.min(getView().getWidth(), getView().getHeight()) * 0.1);
//            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, mapView.getWidth(), mapView.getHeight(), paddingInPixels);
//            map.animateCamera(cu);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void showSignUpInvitation() {

        new MaterialDialog.Builder(getActivity())
                .title(R.string.app_sign_up_invite_dlg_title)
                .content(R.string.app_sign_up_invite_dlg_msg)
                .positiveText(R.string.app_sign_up_invite_dlg_ok_option)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        navigationListener.onNextFragment(FragmentLoginSignUp.class);
                    }
                })
                .show();
    }

//    private LatLng getLatLng(String lat, String lng) {
//        LatLng ll = null;
//
//        if (lat.length() == 6 && lng.length() == 7) {
//            double hoursLat = Double.valueOf(lat.substring(0, 2));
//            double minLat = Double.valueOf(lat.substring(2, 4));
//            double secLat = Double.valueOf(lat.substring(4, 6));
//
//            double hoursLng = Double.valueOf(lng.substring(0, 3));
//            double minLng = Double.valueOf(lng.substring(3, 5));
//            double secLng = Double.valueOf(lng.substring(5, 7));
//
//            double decimalLat = Math.signum(hoursLat) * (Math.abs(hoursLat) + (minLat / 60.0) + (secLat / 3600.0));
//            double decimalLng = Math.signum(hoursLng) * (Math.abs(hoursLng) + (minLng / 60.0) + (secLng / 3600.0));
//
//            ll = new LatLng(decimalLat, -decimalLng);
//        }
//
//        return ll;
//    }
}