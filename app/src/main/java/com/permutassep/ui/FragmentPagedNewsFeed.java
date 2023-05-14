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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentPostListBinding;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.interfaces.FirstLaunchCompleteListener;
import com.permutassep.presentation.interfaces.FloatingActionButtonClickListener;
import com.permutassep.presentation.interfaces.FragmentMenuItemSelectedListener;
import com.permutassep.presentation.interfaces.PostListListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PagedPostListPresenter;
import com.permutassep.presentation.utils.ComplexPreferences;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.PagedPostsListView;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.presentation.view.adapter.PostsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class FragmentPagedNewsFeed extends BaseFragment implements PagedPostsListView {

    public static final String TAG = "FragmentPagedPostList";

    private CaFragmentPostListBinding binding;

    @Inject
    PagedPostListPresenter postListPresenter;

    private MaterialDialog progressDialog;
    private PostComponent postComponent;
    private FloatingActionButtonClickListener floatingActionButtonClickListener;
    private FirstLaunchCompleteListener firstLaunchCompleteListener;
    private FragmentMenuItemSelectedListener fragmentMenuItemSelectedListener;
    private PostsAdapter postsAdapter;
    private PostsLayoutManager postsLayoutManager;
    private PostListListener postListListener;
    private boolean hasNextPage;
    private int currentPage = 1;

    private PostsAdapter.OnItemClickListener onItemClickListener = new PostsAdapter.OnItemClickListener() {
        @Override
        public void onPostItemClicked(PostModel postModel) {
            postListPresenter.onPostClicked(postModel);
        }
    };

    public FragmentPagedNewsFeed() {
        super();
    }

    public static FragmentPagedNewsFeed newInstance() {
        return new FragmentPagedNewsFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        binding.floatingActionButton.setVisibility(PrefUtils.getUser(requireActivity()) == null ? View.GONE : View.VISIBLE);
        postsLayoutManager = new PostsLayoutManager(getActivity());
        binding.rvUsers.setLayoutManager(postsLayoutManager);

        postsAdapter = new PostsAdapter(requireActivity(), new ArrayList<>());
        postsAdapter.setOnItemClickListener(onItemClickListener);
        binding.rvUsers.setAdapter(postsAdapter);
        binding.rvUsers.addOnScrollListener(new EndlessRecyclerOnScrollListener(postsLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                currentPage = current_page;
                if (hasNextPage) {
                    FragmentPagedNewsFeed.this.postListPresenter.initialize(current_page, Config.NEWS_FEED_ITEMS_PER_PAGE);
                }
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> floatingActionButtonClickListener.onFloatingActionButtonClick());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.postListListener = (PostListListener) getActivity();
        this.firstLaunchCompleteListener = (FirstLaunchCompleteListener) getActivity();
        this.fragmentMenuItemSelectedListener = (FragmentMenuItemSelectedListener) getActivity();
        this.floatingActionButtonClickListener = (FloatingActionButtonClickListener) getActivity();
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

    private void initialize() {
        postComponent = DaggerPostComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .build();
        postComponent.inject(this);
        this.postListPresenter.setView(this);
    }

    private void loadUserList() {
        this.postListPresenter.initialize(currentPage, Config.NEWS_FEED_ITEMS_PER_PAGE);
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Methods from the implemented interface PostsListView
     */

    @Override
    public void renderPostList(Collection<PostModel> postModelCollection, boolean hasNextPage) {
        firstLaunchCompleteListener.onFirstLaunchComplete();
        this.hasNextPage = hasNextPage;
        if (postModelCollection != null) {
            this.postsAdapter.setPostsCollection(postModelCollection);
        }
    }

    @Override
    public void viewPostDetail(PostModel postModel) {
        this.postListListener.onPostClicked(postModel);
    }

    @Override
    public void showLoading() {

        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.please_wait)
                    .content(R.string.app_post_list_loading_dlg_msg)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
        } else {
            progressDialog.show();
        }
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        PostModel postModel = ComplexPreferences.get(getActivity()).getObject(ActivityCreatePost.NEW_POST_KEY, PostModel.class);
        if (!hidden && postModel != null) {
            ComplexPreferences.get(getActivity()).removeObject(ActivityCreatePost.NEW_POST_KEY);
            this.postsAdapter.addPost(postModel);
        }
    }

    /**
     * Synchronize with the fragment lifecycle by calling
     * the corresponding presenter methods
     */
    @Override
    public void onResume() {
        super.onResume();
        this.postListPresenter.resume();
        PostModel postModel = ComplexPreferences.get(getActivity()).getObject(ActivityCreatePost.NEW_POST_KEY, PostModel.class);
        if (postModel != null) {
            ComplexPreferences.get(getActivity()).removeObject(ActivityCreatePost.NEW_POST_KEY);
            this.postsAdapter.addPost(postModel);
        }
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

    public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        public String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
        int firstVisibleItem, visibleItemCount, totalItemCount;
        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 2; // The minimum amount of items to have below your current scroll position before loading more.
        private int current_page = 1;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                current_page++;
                onLoadMore(current_page);
                loading = true;
            }
        }

        public abstract void onLoadMore(int current_page);
    }
}
