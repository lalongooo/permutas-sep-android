package com.permutassep.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paging.listview.PagingBaseAdapter;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.utils.TimeAgo;
import com.permutassep.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


public class MyPagingAdapter extends PagingBaseAdapter<Post> {


    private Activity activity;
    private HashMap<String, State> states;
    private TextView tvUserName;
    private TextView tvPostUserEmail;
    private TextView tvFromLabel;
    private TextView tvToLabel;
    private TextView tvAcademicLevelLabel;
    private TextView tvPostDate;
    private TextView tvPostText;
    private TextView tvPostUserPhone;

    public MyPagingAdapter(Activity a){
        super();
        activity = a;
        states = Utils.getStates(a);
    }

    public void addMoreItems(int location, List<Post> newItems) {
        this.items.addAll(location, newItems);
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Post getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(com.lalongooo.permutassep.R.layout.list_item_post, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(com.lalongooo.permutassep.R.id.imageView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        tvUserName = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvUserName);
        tvPostUserEmail = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvPostUserEmail);
        tvFromLabel = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvFrom);
        tvToLabel = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvTo);
        tvAcademicLevelLabel = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvAcademicLevelLabel);
        tvPostDate = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvPostDate);
        tvPostText = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvPostText);
        tvPostUserPhone = (TextView) view.findViewById(com.lalongooo.permutassep.R.id.tvPostUserPhone);

        Post p = getItem(position);

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
                .placeholder(com.lalongooo.permutassep.R.drawable.default_profile_picture)
                .error(com.lalongooo.permutassep.R.drawable.default_profile_picture)
                .resizeDimen(com.lalongooo.permutassep.R.dimen.list_detail_image_size, com.lalongooo.permutassep.R.dimen.list_detail_image_size)
                .centerInside()
                .tag(activity)
                .into(holder.image);
        return view;
    }

    static class ViewHolder {
        ImageView image;
    }
}
