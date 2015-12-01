package com.permutassep.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.navigation.Navigator;
import com.permutassep.presentation.utils.PrefUtils;

import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);

        boolean isPasswordResetActivity = getIntent().getStringExtra(Config.PWD_RESET_TOKEY_KEY) != null;

        if (!PrefUtils.isTosAccepted(this) && !isPasswordResetActivity) {
            Intent intent = new Intent(this, ActivityWelcome.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == 0) {
            // Get tracker.
            Tracker t = ((AndroidApplication) getApplication()).getTracker();
            // Set screen name.
            t.setScreenName(getClass().getName());
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
