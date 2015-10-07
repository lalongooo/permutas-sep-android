package com.permutassep.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.view.fragment.PostDetailsFragment;

/**
 * Created by lalongooo on 27/09/15.
 */
public class PostDetailsActivity extends BaseActivity implements HasComponent<PostComponent> {

    private static final String INTENT_EXTRA_PARAM_POST_ID = "INTENT_PARAM_POST_ID";
    private static final String INSTANCE_STATE_PARAM_POST_ID = "STATE_PARAM_POST_ID";

    private PostComponent postComponent;
    private int postId;

    public static Intent getCallingIntent(Context context, int userId) {
        Intent callingIntent = new Intent(context, PostDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_POST_ID, userId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ca_activity_post_details);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_POST_ID, this.postId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.postId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_POST_ID, -1);
            addFragment(R.id.fl_fragment, PostDetailsFragment.newInstance(this.postId));
        }
    }

    private void initializeInjector() {
        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .postModule(new PostModule(this.postId))
                .build();
    }

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }
}
