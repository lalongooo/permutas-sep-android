package com.permutassep.presentation.view.activity;

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

    private PostComponent postComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ca_activity_post_list);

        this.initializeInjector();
    }

    private void initializeInjector() {
        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }

    @Override
    public void onPostClicked(PostModel postModel) {
        this.navigator.navigateToPostDetails(this, postModel.getId());
    }
}