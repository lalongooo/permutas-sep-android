package com.permutassep.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalongooo.permutassep.R;
import com.permutassep.BaseFragment;
import com.permutassep.adapter.PostAdapter;
import com.permutassep.config.Config;
import com.permutassep.interfaces.FirstLaunchCompleteListener;
import com.permutassep.model.Post;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentNewsFeed extends BaseFragment {

    private ProgressDialog pDlg;
    private PostAdapter adapter;
    private FragmentPostDetail.OnPostItemSelectedListener onPostItemSelectedListener;
    private FirstLaunchCompleteListener firstLaunchCompleteListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(!(getActivity() instanceof FragmentPostDetail.OnPostItemSelectedListener)) {
            throw new ClassCastException("Activity must implement OnPostItemSelectedListener");
        } else {
            onPostItemSelectedListener = (FragmentPostDetail.OnPostItemSelectedListener) getActivity();
        }

        if(!(getActivity() instanceof FirstLaunchCompleteListener)) {
            throw new ClassCastException("Activity must implement OnFirstLoadCompleteListener");
        } else {
            firstLaunchCompleteListener = (FirstLaunchCompleteListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        getActivity().setTitle(R.string.app_name);
        //getActivity().invalidateOptionsMenu();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        final ListView lv = (ListView) rootView.findViewById(R.id.news_feed_list);

        if(!Utils.isNetworkAvailable(getActivity())){
            Utils.showSimpleDialog(R.string.network_availability_dlg_text, R.string.accept, getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            return rootView;
        }

        if(adapter == null){
            showDialog(getString(R.string.app_news_feed_dlg_title), getString(R.string.app_news_feed_dlg_text));
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setDateFormat(Config.APP_DATE_FORMAT);
            Gson gson = gsonBuilder.create();
            new PermutasSEPRestClient(new GsonConverter(gson)).get().getPosts(new Callback<List<Post>>() {
                // new PermutasSEPRestClient().get().getPosts(new Callback<List<Post>>() {
                @Override
                public void success(List<Post> posts, Response response) {
                    if(!posts.isEmpty()){
                        adapter = new PostAdapter(getActivity(), posts);
                        lv.setAdapter(adapter);
                        hideDialog();
                        firstLaunchCompleteListener.onFirstLaunchComplete();
                    }else{
                        hideDialog();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    hideDialog();
                    Utils.showSimpleDialog(R.string.app_news_feed_get_posts_err, R.string.accept, getActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
            });
        }else{
            lv.setAdapter(adapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPostItemSelectedListener.showPostDetail(((Post)adapter.getItem(position)));
            }
        });

        return rootView;
    }

    private void showDialog(String title, String text) {
        pDlg = ProgressDialog.show(getActivity(), title, text, true);
    }

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }
}