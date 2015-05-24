package com.permutassep.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseFragment;
import com.permutassep.adapter.PostAdapter;
import com.permutassep.model.Post;

import java.util.List;

public class FragmentResult extends BaseFragment {

    private List<Post> posts;
    private PostAdapter adapter;
    private FragmentPostDetail.OnPostItemSelectedListener onPostItemSelectedListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(!(getActivity() instanceof FragmentPostDetail.OnPostItemSelectedListener)) {
            throw new ClassCastException("Activity must implement OnPostItemSelectedListener");
        } else {
            onPostItemSelectedListener = (FragmentPostDetail.OnPostItemSelectedListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        final ListView lv = (ListView) rootView.findViewById(R.id.news_feed_list);
        adapter = new PostAdapter(getActivity(), getPosts());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPostItemSelectedListener.showPostDetail(((Post)adapter.getItem(position)));
            }
        });

        return rootView;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}