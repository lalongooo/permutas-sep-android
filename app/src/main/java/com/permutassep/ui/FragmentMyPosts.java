package com.permutassep.ui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentPostListBinding;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.permutassep.presentation.interfaces.FirstLaunchCompleteListener;
import com.permutassep.presentation.interfaces.FragmentMenuItemSelectedListener;
import com.permutassep.presentation.interfaces.PostListListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.UserPostListPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.PostsListView;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.presentation.view.adapter.PostsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class FragmentMyPosts extends BaseFragment implements PostsListView {

    private static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";
    @Inject
    UserPostListPresenter postListPresenter;

    private CaFragmentPostListBinding binding;

    private int userId;
    private MaterialDialog progressDialog;

    private PostComponent postComponent;
    private FirstLaunchCompleteListener firstLaunchCompleteListener;
    private FragmentMenuItemSelectedListener fragmentMenuItemSelectedListener;
    private PostsAdapter postsAdapter;
    private PostsLayoutManager postsLayoutManager;
    private PostListListener postListListener;

    private PostsAdapter.OnItemClickListener onItemClickListener = postModel -> {
        if (FragmentMyPosts.this.postListPresenter != null && postModel != null) {
            FragmentMyPosts.this.postListPresenter.onPostClicked(postModel);
        }
    };

    public static FragmentMyPosts newInstance(int userId) {
        FragmentMyPosts mFragmentMyPosts = new FragmentMyPosts();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_USER_ID, userId);
        mFragmentMyPosts.setArguments(args);

        return mFragmentMyPosts;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentPostListBinding.inflate(inflater, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        setupUI();
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    private void setupUI() {
        postsLayoutManager = new PostsLayoutManager(getActivity());
        binding.rvUsers.setLayoutManager(postsLayoutManager);
        postsAdapter = new PostsAdapter(requireActivity(), new ArrayList<PostModel>());
        postsAdapter.setOnItemClickListener(onItemClickListener);
        binding.rvUsers.setAdapter(postsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.postListListener = (PostListListener) getActivity();
        this.firstLaunchCompleteListener = (FirstLaunchCompleteListener) getActivity();
        this.fragmentMenuItemSelectedListener = (FragmentMenuItemSelectedListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        if (PrefUtils.getUser(getActivity()) != null) {
            menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());
            menu.findItem(R.id.action_logout).setVisible(true);
        }

        menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentMenuItemSelectedListener.onMenuItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadUserPostsList();
    }

    private void initialize() {
        this.userId = getArguments().getInt(ARGUMENT_USER_ID);
        postComponent = DaggerPostComponent.builder().applicationComponent(getComponent(ApplicationComponent.class)).activityModule(((BaseActivity) getActivity()).getActivityModule()).postModule(new PostModule(this.userId)).build();
        postComponent.inject(this);
        this.postListPresenter.setView(this, PrefUtils.getUser(getActivity()));
    }

    private void loadUserPostsList() {
        this.postListPresenter.initialize(this.userId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Methods from the implemented interface PostsListView
     */

    @Override
    public void renderPostList(Collection<PostModel> postModelCollection) {
        if (postModelCollection != null) {
            this.postsAdapter.setPostsCollection(postModelCollection);
        }
        firstLaunchCompleteListener.onFirstLaunchComplete();
    }

    @Override
    public void viewPostDetail(PostModel postModel) {
        if (this.postListListener != null) {
            this.postListListener.onPostClicked(postModel);
        }
    }

    @Override
    public void showLoading() {
        progressDialog = new MaterialDialog.Builder(getActivity()).title(R.string.please_wait).content(R.string.app_post_list_loading_dlg_msg).progress(true, 0).progressIndeterminateStyle(false).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void showRetry() {
        new MaterialDialog.Builder(requireActivity())
                .cancelable(false)
                .title(R.string.ups)
                .content(R.string.app_post_list_retry_dlg_message)
                .positiveText(R.string.retry)
                .onPositive((materialDialog, dialogAction) -> loadUserPostsList())
                .show();
    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.postListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postListPresenter.destroy();
    }
}
