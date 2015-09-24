package com.permutassep.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PostListPresenter;
import com.permutassep.presentation.view.PostsListView;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.presentation.view.adapter.PostsLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PostListFragment extends BaseFragment implements PostsListView {

    @Inject
    PostListPresenter postListPresenter;

    @Bind(R.id.rv_users)
    RecyclerView rv_users;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private PostsAdapter postsAdapter;
    private PostsLayoutManager postsLayoutManager;

    public PostListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_post_list, container, true);
        ButterKnife.bind(this, fragmentView);
        setupUI();

        return fragmentView;
    }


    private void setupUI() {
        this.postsLayoutManager = new PostsLayoutManager(getActivity());
        this.rv_users.setLayoutManager(postsLayoutManager);

        this.postsAdapter = new PostsAdapter(getActivity(), new ArrayList<PostModel>());
        this.rv_users.setAdapter(postsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadUserList();
    }

    private void initialize() {
        this.getComponent(PostComponent.class).inject(this);
        this.postListPresenter.setView(this);
    }

    private void loadUserList() {
        this.postListPresenter.initialize();
    }


    @Override
    public void renderPostList(Collection<PostModel> postModelCollection) {

    }

    @Override
    public void viewPostDetail(PostModel postModel) {

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
