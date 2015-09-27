package com.permutassep.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.model.PostModel;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    public interface OnItemClickListener {
        void onPostItemClicked(PostModel postModel);
    }

    private OnItemClickListener onItemClickListener;

    private List<PostModel> postModelList;
    private final LayoutInflater layoutInflater;

    public PostsAdapter(Context context, Collection<PostModel> postModelCollection) {
        this.validatePostsCollection(postModelCollection);
        this.postModelList = (List<PostModel>) postModelCollection;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        final PostModel userModel = this.postModelList.get(position);
        holder.textViewTitle.setText(userModel.getPostText());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPostsCollection(Collection<PostModel> postsCollection) {
        this.validatePostsCollection(postsCollection);
        this.postModelList = (List<PostModel>) postsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}