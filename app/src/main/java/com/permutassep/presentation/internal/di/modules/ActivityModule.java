package com.permutassep.presentation.internal.di.modules;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    Toolbar providesToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        appCompatActivity.setSupportActionBar(toolbar);
        return toolbar;
    }
}