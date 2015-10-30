package com.permutassep.presentation.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lalongooo.permutassep.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.permutassep.presentation.interfaces.FirstLaunchCompleteListener;
import com.permutassep.presentation.interfaces.FragmentMenuItemSelectedListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PostListPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.PostsListView;
import com.permutassep.presentation.view.activity.BaseActivity;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.presentation.view.adapter.PostsLayoutManager;
import com.permutassep.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class FragmentPostList extends BaseFragment implements PostsListView {

    @Inject
    PostListPresenter postListPresenter;

    @Bind(R.id.rv_users)
    RecyclerView rv_posts;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private PostComponent postComponent;

    private FirstLaunchCompleteListener firstLaunchCompleteListener;
    private FragmentMenuItemSelectedListener fragmentMenuItemSelectedListener;
    private PostsAdapter postsAdapter;
    private PostsLayoutManager postsLayoutManager;
    private PostListListener postListListener;

    private PostsAdapter.OnItemClickListener onItemClickListener = new PostsAdapter.OnItemClickListener() {
        @Override
        public void onPostItemClicked(PostModel postModel) {
            if (FragmentPostList.this.postListPresenter != null && postModel != null) {
                FragmentPostList.this.postListPresenter.onPostClicked(postModel);
            }
        }
    };


    public FragmentPostList() {
        super();
    }

    public static FragmentPostList newInstance() {
        return new FragmentPostList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_post_list, container, false);
        ButterKnife.bind(this, fragmentView);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        setupUI();
        setHasOptionsMenu(true);
        return fragmentView;
    }

    private void setupUI() {
        this.postsLayoutManager = new PostsLayoutManager(getActivity());
        this.rv_posts.setLayoutManager(postsLayoutManager);

        this.postsAdapter = new PostsAdapter(getActivity(), new ArrayList<PostModel>());
        this.postsAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_posts.setAdapter(postsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.postListListener = (PostListListener) getActivity();
        this.firstLaunchCompleteListener = (FirstLaunchCompleteListener) getActivity();
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
            menu.findItem(R.id.action_post).setIcon(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBarSize()).setVisible(true);
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
                .activityModule(((BaseActivity)getActivity()).getActivityModule())
                .build();
        postComponent.inject(this);
        this.postListPresenter.setView(this);
    }

    private void loadUserList() {
        this.postListPresenter.initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        FragmentPostList.this.loadUserList();
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

    /**
     * Interface for listening post list item click events.
     */
    public interface PostListListener {
        void onPostClicked(final PostModel postModel);
    }
}