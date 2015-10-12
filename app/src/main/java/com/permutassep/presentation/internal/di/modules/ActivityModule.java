package com.permutassep.presentation.internal.di.modules;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lalongooo.permutassep.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.permutassep.model.User;
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
    Drawer providesDrawer(Toolbar toolbar, AccountHeader accountHeader) {

        return new DrawerBuilder()
                .withActivity(activity)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar)
                .withSelectedItem(0)
                .build();
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

    @Provides
    @PerActivity
    AccountHeader providesAccountHeader(IProfile iProfile) {

        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(iProfile)
                .build();
    }

    @Provides
    @PerActivity
    IProfile providesIProfile(User user) {
        IProfile profile = new ProfileDrawerItem()
                .withName(user.getName())
                .withEmail(user.getEmail())
                .withIcon(Uri.parse("https://graph.facebook.com/" + user.getSocialUserId() + "/picture?width=460&height=460"));

        return profile;
    }
}