package com.permutassep.presentation;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.lalongooo.permutassep.BuildConfig;
import com.lalongooo.permutassep.R;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerApplicationComponent;
import com.permutassep.presentation.internal.di.modules.ApplicationModule;
import com.squareup.picasso.Picasso;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeGoogleAnalytics();
        this.setUpDrawerImageLoader();
        this.setupParseCom();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    /**
     * Initialize the {@link ApplicationComponent} for dependency injection
     */
    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * Initialize the Google Analytics objects, a {@link Tracker} and a {@link GoogleAnalytics} instance
     */
    private void initializeGoogleAnalytics() {
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setDryRun(BuildConfig.DEBUG);

        tracker = analytics.newTracker(R.xml.global_tracker);
        tracker.enableAdvertisingIdCollection(true);
    }

    /**
     * Setup Drawer ImageLoader to load profile pictures
     * for the navigation drawer profile.
     */
    private void setUpDrawerImageLoader() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
    }

    /**
     * Retrieves the {@link ApplicationComponent} for dependency injection.
     *
     * @return an {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**
     * Retrieves the Tracker object to send hits to of Google Analytics
     *
     * @return A {@link Tracker} object
     */
    public synchronized Tracker getTracker() {

        if (analytics == null || tracker == null) {
            initializeGoogleAnalytics();
        }
        return tracker;
    }

    /**
     * Setup parse.com according to https://parse.com/apps/quickstart#parse_push/android/native/existing
     */
    private void setupParseCom() {
        Parse.initialize(this, BuildConfig.PARSE_COM_APPLICATION_ID, BuildConfig.PARSE_COM_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}