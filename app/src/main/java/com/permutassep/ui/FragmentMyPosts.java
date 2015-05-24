package com.permutassep.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.permutassep.adapter.PostTypeAdapter;
import com.permutassep.config.Config;
import com.permutassep.model.Post;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentMyPosts extends BaseFragment {

    private Activity mActiviy;
    private ProgressDialog pDlg;
    private PostAdapter adapter;
    private FragmentPostDetail.OnPostItemSelectedListener onPostItemSelectedListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!(getActivity() instanceof FragmentPostDetail.OnPostItemSelectedListener)) {
            throw new ClassCastException("Activity must implement OnPostItemSelectedListener");
        } else {
            onPostItemSelectedListener = (FragmentPostDetail.OnPostItemSelectedListener) getActivity();
            this.mActiviy = activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
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

        showDialog(getString(R.string.app_news_feed_dlg_title), getString(R.string.app_news_feed_dlg_text));
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeHierarchyAdapter(Post.class, new PostTypeAdapter(getActivity()))
                .setDateFormat(Config.APP_DATE_FORMAT);
        Gson gson = gsonBuilder.create();
        new PermutasSEPRestClient(new GsonConverter(gson)).get().myPosts(Utils.getUser(getActivity()).getId(), new Callback<List<Post>>() {
            @Override
            public void success(List<Post> posts, Response response) {
                if(!posts.isEmpty()){
                    adapter = new PostAdapter(mActiviy, posts);
                    lv.setAdapter(adapter);
                    hideDialog();
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