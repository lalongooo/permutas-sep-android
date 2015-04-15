package com.permutassep.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.permutassep.R;
import com.permutassep.adapter.PostAdapter;
import com.permutassep.config.Config;
import com.permutassep.model.Post;
import com.permutassep.model.User;
import com.permutassep.rest.PermutasSEPRestClient;
import com.permutassep.utils.PostTypeAdapter;
import com.permutassep.utils.UserTypeAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentNewsFeed extends Fragment {

    private ProgressDialog pDlg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        final ListView lv = (ListView) rootView.findViewById(R.id.news_feed_list);

        showDialog(getString(R.string.app_news_feed_dlg_title), getString(R.string.app_news_feed_dlg_text));
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeHierarchyAdapter(User.class, new UserTypeAdapter(getActivity()))
                .registerTypeHierarchyAdapter(Post.class, new PostTypeAdapter(getActivity()))
                .setDateFormat(Config.APP_DATE_FORMAT);
        Gson gson = gsonBuilder.create();
        new PermutasSEPRestClient(new GsonConverter(gson)).get().getPosts(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> posts, Response response) {
                if(!posts.isEmpty()){
                    PostAdapter adapter = new PostAdapter(getActivity(), posts);
                    lv.setAdapter(adapter);
                    hideDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Add the error message dialog
                hideDialog();
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