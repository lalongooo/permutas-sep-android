package com.permutassep.presentation.view.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.PagedPostListPresenter;
import com.permutassep.presentation.view.PagedPostsListView;
import com.permutassep.presentation.view.activity.ActivityMain;
import com.permutassep.presentation.view.activity.BaseActivity;
import com.permutassep.presentation.view.adapter.PostsAdapter;
import com.permutassep.ui.FragmentPostDetail;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class TestRecyclerView extends BaseFragment implements PagedPostsListView {

    @Bind(R.id.rv_users)
    RecyclerView rv_posts;
    private PostsAdapter postsAdapter;

    @Inject
    PagedPostListPresenter postListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.test_ca_fragment_post_list, container, false);
        ButterKnife.bind(this, fragmentView);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }


        this.rv_posts.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.postsAdapter = new PostsAdapter(getActivity(), new ArrayList<PostModel>());
        this.postsAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onPostItemClicked(PostModel postModel) {
                Log.i("Item clicked", "Post clicked " + postModel.getId() + " - " + postModel.getPostText());
                navigationListener.onNextFragment(FragmentPagedPostList.class);
            }
        });
        this.rv_posts.setAdapter(postsAdapter);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle("Test");
        PostComponent postComponent = DaggerPostComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .build();
        postComponent.inject(this);
        postListPresenter.setView(this);
        postListPresenter.initialize(1, 50);
    }

    @Override
    public void renderPostList(Collection<PostModel> postModelCollection, boolean hasNextPage) {
        this.postsAdapter.setPostsCollection(postModelCollection);
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