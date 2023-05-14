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
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentPostListBinding;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.permutassep.presentation.interfaces.FragmentMenuItemSelectedListener;
import com.permutassep.presentation.interfaces.PostListListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.SearchPostsResultsPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.SearchPostsResultsView;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.presentation.view.adapter.PostsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class FragmentResult extends BaseFragment implements SearchPostsResultsView {

    private static final String ARGUMENT_SEARCH_PARAMS = "argument_search_params";

    private CaFragmentPostListBinding binding;

    @Inject
    SearchPostsResultsPresenter postListPresenter;

    private MaterialDialog progressDialog;
    private HashMap<String, String> searchParams;
    private PostComponent postComponent;
    private FragmentMenuItemSelectedListener fragmentMenuItemSelectedListener;
    private PostsAdapter postsAdapter;
    private PostsLayoutManager postsLayoutManager;
    private PostListListener postListListener;

    public static FragmentResult newInstance(HashMap<String, String> searchParams) {

        FragmentResult mFragmentResult = new FragmentResult();

        Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_SEARCH_PARAMS, searchParams);
        mFragmentResult.setArguments(args);

        return mFragmentResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentPostListBinding.inflate(inflater, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        binding.floatingActionButton.setVisibility(View.GONE);
        postsLayoutManager = new PostsLayoutManager(getActivity());
        binding.rvUsers.setLayoutManager(postsLayoutManager);
        postsAdapter = new PostsAdapter(requireActivity(), new ArrayList<>());
        postsAdapter.setOnItemClickListener(onItemClickListener);
        binding.rvUsers.setAdapter(postsAdapter);
    }

    private PostsAdapter.OnItemClickListener onItemClickListener = postModel -> {
        if (FragmentResult.this.postListPresenter != null && postModel != null) {
            FragmentResult.this.postListPresenter.onPostClicked(postModel);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.postListListener = (PostListListener) getActivity();
        this.fragmentMenuItemSelectedListener = (FragmentMenuItemSelectedListener) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadUserList();
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

    @SuppressWarnings("unchecked")
    private void initialize() {
        this.searchParams = (HashMap<String, String>) getArguments().getSerializable(ARGUMENT_SEARCH_PARAMS);

        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .postModule(new PostModule(searchParams))
                .build();
        this.postComponent.inject(this);
        this.postListPresenter.setView(this);
    }

    private void loadUserList() {
        this.postListPresenter.initialize();
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
    }

    @Override
    public void viewPostDetail(PostModel postModel) {
        if (this.postListListener != null) {
            this.postListListener.onPostClicked(postModel);
        }
    }

    @Override
    public void showEmptyResultsMessage() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.search_fragment_results_empty_dlg_title)
                .content(R.string.search_fragment_results_empty_dlg_msg)
                .cancelable(false)
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .show();
    }

    @Override
    public void showLoading() {

        progressDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.please_wait)
                .content(R.string.app_post_list_loading_dlg_msg)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showRetry() {
        new MaterialDialog.Builder(getActivity())
                .cancelable(false)
                .title(R.string.ups)
                .content(R.string.app_post_list_retry_dlg_message)
                .positiveText(R.string.retry)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        loadUserList();
                    }
                })
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
