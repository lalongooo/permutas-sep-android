package com.permutassep.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalongooo.permutassep.R;
import com.paging.listview.PagingListView;
import com.permutassep.adapter.MyPagingAdapter;
import com.permutassep.config.Config;
import com.permutassep.model.PostPage;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class MainActivityFragment extends Fragment
        implements PagingListView.Pagingable, SwipeRefreshLayout.OnRefreshListener{

    public MainActivityFragment() {
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private PagingListView listView;
    private MyPagingAdapter adapter;
    private int page = 1;
    private boolean loadMore = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        listView = (PagingListView) rootView.findViewById(R.id.listView);
        listView.setPagingableListener(MainActivityFragment.this);
        listView.setHasMoreItems(true);

        return rootView;
    }

    private void getPosts() {

        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(Config.APP_DATE_FORMAT);
        Gson gson = gsonBuilder.create();

        new PermutasSEPRestClient(new GsonConverter(gson)).get().getPostPage(page, Config.NEWS_FEED_ITEMS_PER_PAGE, new Callback<PostPage>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(PostPage postPage, Response response) {
                if (postPage.getNext() != null) {
                    page = Integer.valueOf(Utils.splitQuery(postPage.getNext()).get("page").get(0));
                }

                adapter.addMoreItems(0, postPage.getResults());
                int index = listView.getFirstVisiblePosition() + postPage.getResults().size() - 1;
                int top = (listView.getChildAt(0) == null) ? 0 : listView.getChildAt(0).getTop();
                adapter.notifyDataSetChanged();
                listView.setSelectionFromTop(index, top);

                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    @Override
    public void onLoadMoreItems() {
        if (loadMore) {
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(Config.APP_DATE_FORMAT);
            Gson gson = gsonBuilder.create();
            new PermutasSEPRestClient(new GsonConverter(gson)).get().getPostPage(page, Config.NEWS_FEED_ITEMS_PER_PAGE, new Callback<PostPage>() {
                @Override
                public void success(PostPage postPage, Response response) {
                    if (postPage.getNext() != null) {
                        page = Integer.valueOf(Utils.splitQuery(postPage.getNext()).get("page").get(0));
                    } else {
                        loadMore = false;
                    }
                    if (adapter == null) {
                        adapter = new MyPagingAdapter(getActivity());
                        listView.setAdapter(adapter);
                    }
                    listView.onFinishLoading(true, postPage.getResults());
                }

                @Override
                public void failure(RetrofitError error) { }
            });
        } else {
            listView.onFinishLoading(false, null);
        }
    }

    @Override
    public void onRefresh() {
        getPosts();
    }
}