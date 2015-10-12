package com.permutassep.presentation.internal.di.modules;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lalongooo.permutassep.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.permutassep.model.User;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.view.activity.MainActivity;

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
    Drawer.Result providesDrawerResult(Toolbar toolbar, AccountHeader.Result headerResult) {
        Drawer.Result drawerResult = new Drawer()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(

                        new PrimaryDrawerItem()
                                .withName(activity.getString(R.string.app_nav_drawer_1))
                                .withIdentifier(MainActivity.DRAWER_IDENTIFIER_HOME)
                                .withIcon(GoogleMaterial.Icon.gmd_home),

                        new PrimaryDrawerItem()
                                .withName(activity.getString(R.string.app_nav_drawer_2))
                                .withIdentifier(MainActivity.DRAWER_IDENTIFIER_MY_POSTS)
                                .withIcon(GoogleMaterial.Icon.gmd_content_paste)
                )
                .withSelectedItem(0)
                .build();
        drawerResult.getListView().setVerticalScrollBarEnabled(false);

        return drawerResult;
    }

    @Provides
    @PerActivity
    Toolbar providesToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        return (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
    }

    @Provides
    @PerActivity
    AccountHeader.Result providesAccountHeaderResult(IProfile iProfile) {

        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(iProfile)
                .build();

        return headerResult;

    }

    @Provides
    @PerActivity
    IProfile proviesIProfile(User user) {
        IProfile profile = new ProfileDrawerItem()
                .withName(user.getName())
                .withEmail(user.getEmail())
                .withIcon(Uri.parse("https://graph.facebook.com/" + user.getSocialUserId() + "/picture?width=460&height=460"));

        return profile;
    }
}