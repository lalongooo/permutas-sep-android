package com.permutassep.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalongooo.permutassep.R;
import com.paging.listview.PagingListView;
import com.permutassep.BaseFragment;
import com.permutassep.adapter.MyPagingAdaper;
import com.permutassep.config.Config;
import com.permutassep.model.PostPage;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentPagedNewsFeed extends BaseFragment {

    private PagingListView listView;
    private MyPagingAdaper adapter;
    private String page = "1";
    private boolean loadMore = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_paged_news_feed, container, false);
        listView = (PagingListView) rootView.findViewById(R.id.paging_list_view);
        adapter = new MyPagingAdaper(getActivity());
        listView.setAdapter(adapter);
        listView.setHasMoreItems(true);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (loadMore) {
                    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(Config.APP_DATE_FORMAT);
                    Gson gson = gsonBuilder.create();
                    new PermutasSEPRestClient(new GsonConverter(gson)).get().getPostPage(page, "100", new Callback<PostPage>() {
                        @Override
                        public void success(PostPage postPage, Response response) {
                            if(postPage.getNext() != null){
                                page = Utils.splitQuery(postPage.getNext()).get("page").get(0);
                            }else{
                                loadMore = false;
                            }
                            listView.onFinishLoading(true, postPage.getResults());
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                } else {
                    listView.onFinishLoading(false, null);
                }
            }
        });

        return rootView;
    }
}