package com.permutassep.presentation.view.activity;

import android.os.Bundle;

import com.lalongooo.permutassep.R;
import com.mikepenz.materialdrawer.Drawer;
import com.permutassep.presentation.internal.di.components.ActivityComponent;
import com.permutassep.presentation.internal.di.components.DaggerActivityComponent;

import javax.inject.Inject;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {


    public static final int DRAWER_IDENTIFIER_HOME = 1;
    public static final int DRAWER_IDENTIFIER_MY_POSTS = 1;

    @Inject
    Drawer.Result result;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initializeInjector();
        activityComponent.inject(this);
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
