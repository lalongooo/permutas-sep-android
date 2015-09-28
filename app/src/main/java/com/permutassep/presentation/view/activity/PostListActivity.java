package com.permutassep.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.view.fragment.PostListFragment;

/**
 * Created by lalongooo on 13/09/15.
 */
public class PostListActivity extends BaseActivity implements HasComponent<PostComponent>,
        PostListFragment.PostListListener {

    private PostComponent userComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PostListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ca_activity_post_list);

        this.initializeInjector();
    }

    private void initializeInjector() {
        this.userComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PostComponent getComponent() {
        return userComponent;
    }

    @Override
    public void onPostClicked(PostModel postModel) {

    }
}