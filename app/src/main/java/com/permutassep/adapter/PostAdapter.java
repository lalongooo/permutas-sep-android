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
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.utils.TimeAgo;
import com.permutassep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends BaseAdapter {
    private Activity activity;
    private List<Post> data;
    private HashMap<String, State> states;

    private TextView tvUserName;
    private TextView tvPostUserEmail;
    private TextView tvFromLabel;
    private TextView tvToLabel;
    private TextView tvAcademicLevelLabel;
    private TextView tvPostDate;
    private TextView tvPostText;
    private TextView tvPostUserPhone;

    public PostAdapter(Activity a, List<Post> posts) {
        activity = a;
        data = posts;
        states = Utils.getStates(a);
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

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item_post, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvPostUserEmail = (TextView) view.findViewById(R.id.tvPostUserEmail);
        tvFromLabel = (TextView) view.findViewById(R.id.tvFrom);
        tvToLabel = (TextView) view.findViewById(R.id.tvTo);
        tvAcademicLevelLabel = (TextView) view.findViewById(R.id.tvAcademicLevelLabel);
        tvPostDate = (TextView) view.findViewById(R.id.tvPostDate);
        tvPostText = (TextView) view.findViewById(R.id.tvPostText);
        tvPostUserPhone = (TextView) view.findViewById(R.id.tvPostUserPhone);

        Post p = data.get(position);

        tvUserName.setText(p.getUser().getName());
        tvPostUserEmail.setText(p.getUser().getEmail());
        tvFromLabel.setText(states.get((String.valueOf(p.getStateFrom()))).getStateName());
        tvToLabel.setText(states.get(String.valueOf(p.getStateTo())).getStateName());
        tvAcademicLevelLabel.setText(p.getAcademicLevel());
        tvPostDate.setText(new TimeAgo(activity).timeAgo(p.getPostDate()));
        tvPostText.setText(p.getPostText());
        tvPostUserPhone.setText(p.getUser().getPhone());

        Picasso.with(activity)
                    .load("https://graph.facebook.com/" + p.getUser().getSocialUserId() + "/picture?width=100&height=100")
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(activity)
                    .into(holder.image);
        return view;
    }

    static class ViewHolder {
        ImageView image;
    }
}