package com.permutassep.presentation;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.app.Application;

import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.lalongooo.permutassep.BuildConfig;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerApplicationComponent;
import com.permutassep.presentation.internal.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**
     * Retrieves the Tracker object to send hits to of Google Analytics
     *
     * @return A {@link Tracker} object
     */
    public synchronized Tracker getTracker() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        analytics.setDryRun(BuildConfig.DEBUG);
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        Tracker t = analytics.newTracker(BuildConfig.google_analytics_property_id);
        t.enableAdvertisingIdCollection(true);
        return t;
    }

}
