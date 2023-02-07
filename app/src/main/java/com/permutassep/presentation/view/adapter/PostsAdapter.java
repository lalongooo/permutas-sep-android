package com.permutassep.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lalongooo.permutassep.R;
import com.permutassep.model.State;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.utils.TimeAgo;
import com.permutassep.presentation.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private HashMap<String, State> states;
    private Context mContext;
    private List<PostModel> postModelList;
    private TimeAgo mTimeAgo;

    public PostsAdapter(Context context, Collection<PostModel> postModelCollection) {
        this.validatePostsCollection(postModelCollection);
        this.mContext = context;
        this.postModelList = (List<PostModel>) postModelCollection;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.states = Utils.getStates(context);
        this.mTimeAgo = new TimeAgo(context);
    }

    private void validatePostsCollection(Collection<PostModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public int getItemCount() {
        return (this.postModelList == null) ? 0 : this.postModelList.size();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.ca_row_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        final PostModel postModel = this.postModelList.get(position);

        holder.tvUserName.setText(postModel.getUser().getName());
        holder.tvPostDate.setText(mTimeAgo.timeAgo(Utils.toDate(postModel.getPostDate())));
        holder.tvPostUserEmail.setText(PrefUtils.isLoggedUser(mContext) ? postModel.getUser().getEmail() : postModel.getUser().getEmail().replace(postModel.getUser().getEmail().substring(0, postModel.getUser().getEmail().indexOf("@")), new String(new char[postModel.getUser().getEmail().substring(0, postModel.getUser().getEmail().indexOf("@")).length()]).replace("\0", "*")));
        holder.tvPostUserPhone.setText(PrefUtils.isLoggedUser(mContext) ? postModel.getUser().getPhone() : postModel.getUser().getPhone().replace(postModel.getUser().getPhone().substring(0, Config.HIDE_PHONE_CHARACTERS), new String(new char[6]).replace("\0", Config.CHARACTER_FOR_ANON_USER)));
        holder.tvPostText.setText(postModel.getPostText());
        holder.tvFrom.setText(states.get((String.valueOf(postModel.getStateFrom()))).getStateName());
        holder.tvTo.setText(states.get(String.valueOf(postModel.getStateTo())).getStateName());
        holder.tvAcademicLevelLabel.setText(postModel.getAcademicLevel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PostsAdapter.this.onItemClickListener != null) {
                    PostsAdapter.this.onItemClickListener.onPostItemClicked(postModel);
                }
            }
        });
        Picasso.with(mContext)
                .load(postModel.getUser().getProfilePictureUrl())
                .placeholder(com.lalongooo.permutassep.R.drawable.default_profile_picture)
                .error(com.lalongooo.permutassep.R.drawable.default_profile_picture)
                .resizeDimen(com.lalongooo.permutassep.R.dimen.list_detail_image_size, com.lalongooo.permutassep.R.dimen.list_detail_image_size)
                .centerInside()
                .into(holder.imageView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPostsCollection(Collection<PostModel> postsCollection) {
        this.validatePostsCollection(postsCollection);
        this.postModelList.addAll(postsCollection);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onPostItemClicked(PostModel postModel);
    }

    public void addPost(PostModel postModel) {
        this.postModelList.add(0, postModel);
        this.notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvPostDate)
        TextView tvPostDate;
        @BindView(R.id.tvPostUserEmail)
        TextView tvPostUserEmail;
        @BindView(R.id.tvPostUserPhone)
        TextView tvPostUserPhone;
        @BindView(R.id.tvPostText)
        TextView tvPostText;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.tvAcademicLevelLabel)
        TextView tvAcademicLevelLabel;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}