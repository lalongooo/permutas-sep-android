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
import com.lalongooo.permutassep.BuildConfig;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.DaggerApplicationComponent;
import com.permutassep.presentation.internal.di.modules.ApplicationModule;
import com.squareup.picasso.Picasso;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.setUpDrawerImageLoader();
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
}