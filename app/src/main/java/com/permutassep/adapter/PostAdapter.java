package com.permutassep.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.utils.TimeAgo;
import com.permutassep.utils.Utils;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private Activity activity;
    private List<Post> data;
    private HashMap<String, State> states;
    private DisplayImageOptions options;

    public PostAdapter(Activity a, List<Post> posts) {
        activity = a;
        data = posts;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        states = Utils.getStates(a);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_profile_picture)
                .showImageForEmptyUri(R.drawable.default_profile_picture)
                .showImageOnFail(R.drawable.default_profile_picture)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(false)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_item_post, null);
        }

        TextView tvUserName = (TextView) vi.findViewById(R.id.tvUserName);
        TextView tvPostUserEmail = (TextView) vi.findViewById(R.id.tvPostUserEmail);
        TextView tvFromLabel = (TextView) vi.findViewById(R.id.tvFrom);
        TextView tvToLabel = (TextView) vi.findViewById(R.id.tvTo);
        TextView tvAcademicLevelLabel = (TextView) vi.findViewById(R.id.tvAcademicLevelLabel);
        TextView tvPostDate = (TextView) vi.findViewById(R.id.tvPostDate);
        TextView tvPostText = (TextView) vi.findViewById(R.id.tvPostText);
        ImageView profilePicture = (ImageView) vi.findViewById(R.id.imageView);

        Post p = data.get(position);

        tvUserName.setText(p.getUser().getName());
        tvPostUserEmail.setText(p.getUser().getEmail());
        tvFromLabel.setText(states.get((String.valueOf(p.getStateFrom()))).getStateName());
        tvToLabel.setText(states.get(String.valueOf(p.getStateTo())).getStateName());
        tvAcademicLevelLabel.setText(p.getAcademicLevel());
        tvPostDate.setText(new TimeAgo(activity).timeAgo(p.getPostDate()));
        tvPostText.setText(p.getPostText());

        if(!TextUtils.isEmpty(p.getUser().getSocialUserId())){
            ImageLoader.getInstance().displayImage("https://graph.facebook.com/" + p.getUser().getSocialUserId() + "/picture?width=200&height=200", profilePicture, options);
        }

        return vi;
    }
}