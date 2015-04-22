package com.permutassep.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.permutassep.R;
import com.permutassep.adapter.PostAdapter;
import com.permutassep.model.Post;

import java.util.List;

public class FragmentResult extends Fragment {

    private List<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        final ListView lv = (ListView) rootView.findViewById(R.id.news_feed_list);
        getActivity().setTitle(R.string.app_main_toolbar_search_results);
        getActivity().invalidateOptionsMenu();

        PostAdapter adapter = new PostAdapter(getActivity(), getPosts());
        lv.setAdapter(adapter);
        return rootView;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}