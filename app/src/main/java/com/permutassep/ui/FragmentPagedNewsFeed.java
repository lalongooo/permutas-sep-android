package com.permutassep.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalongooo.permutassep.R;
import com.paging.listview.PagingListView;
import com.permutassep.BaseFragment;
import com.permutassep.adapter.MyPagingAdapter;
import com.permutassep.config.Config;
import com.permutassep.interfaces.FirstLaunchCompleteListener;
import com.permutassep.model.Post;
import com.permutassep.model.PostPage;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentPagedNewsFeed extends BaseFragment
        implements PagingListView.Pagingable{

    private PagingListView listView;
    private MyPagingAdapter adapter;
    private int page = 1;
    private boolean loadMore = true;
    private FragmentPostDetail.OnPostItemSelectedListener onPostItemSelectedListener;
    private FirstLaunchCompleteListener firstLaunchCompleteListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_paged_news_feed, container, false);

        if (!Utils.isNetworkAvailable(getActivity())) {
            Utils.showSimpleDialog(R.string.network_availability_dlg_text, R.string.accept, getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            return rootView;
        }

        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        listView = (PagingListView) rootView.findViewById(R.id.paging_list_view);
        if (adapter == null) {
            adapter = new MyPagingAdapter(getActivity());
        }
        listView.setAdapter(adapter);
        listView.setHasMoreItems(true);
        listView.setPagingableListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPostItemSelectedListener.showPostDetail(((Post) adapter.getItem(position)));
            }
        });

        return rootView;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(getActivity() instanceof FragmentPostDetail.OnPostItemSelectedListener)) {
            throw new ClassCastException("Activity must implement FragmentPostDetail.OnPostItemSelectedListener");
        } else {
            onPostItemSelectedListener = (FragmentPostDetail.OnPostItemSelectedListener) getActivity();
        }

        if (!(getActivity() instanceof FirstLaunchCompleteListener)) {
            throw new ClassCastException("Activity must implement FirstLaunchCompleteListener");
        } else {
            firstLaunchCompleteListener = (FirstLaunchCompleteListener) getActivity();
        }
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
                        page = Integer.valueOf(Utils.splitQuery(postPage.getNext()).get(0).getValue());
                        firstLaunchCompleteListener.onFirstLaunchComplete();
                    } else {
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
}