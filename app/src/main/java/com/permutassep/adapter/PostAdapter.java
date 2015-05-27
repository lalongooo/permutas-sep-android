package com.permutassep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class PostAdapter extends ArrayAdapter<Post> {
    private Context context;
    private List<Post> posts;
    private HashMap<String, State> states;

    private TextView tvUserName;
    private TextView tvPostUserEmail;
    private TextView tvFromLabel;
    private TextView tvToLabel;
    private TextView tvAcademicLevelLabel;
    private TextView tvPostDate;
    private TextView tvPostText;
    private TextView tvPostUserPhone;


    public PostAdapter(Context context, List<Post> posts) {
        super(context, -1, posts);
        this.context = context;
        this.posts = posts;
        states = Utils.getStates(context);
    }

    public int getCount() {
        return posts.size();
    }

    public Post getItem(int position) {
        return posts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_post, parent, false);
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

        Post p = posts.get(position);

        tvUserName.setText(p.getUser().getName());
        tvPostUserEmail.setText(p.getUser().getEmail());
        tvFromLabel.setText(states.get((String.valueOf(p.getStateFrom()))).getStateName());
        tvToLabel.setText(states.get(String.valueOf(p.getStateTo())).getStateName());
        tvAcademicLevelLabel.setText(p.getAcademicLevel());
        tvPostDate.setText(new TimeAgo(context).timeAgo(p.getPostDate()));
        tvPostText.setText(p.getPostText());
        tvPostUserPhone.setText(p.getUser().getPhone());

        Picasso.with(context)
                    .load("https://graph.facebook.com/" + p.getUser().getSocialUserId() + "/picture?width=100&height=100")
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                    .centerInside()
                    .tag(context)
                    .into(holder.image);
        return view;
    }

    static class ViewHolder {
        ImageView image;
    }
}