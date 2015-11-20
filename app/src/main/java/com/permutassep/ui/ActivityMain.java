package com.permutassep.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.lalongooo.permutassep.R;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.permutassep.BaseActivity;
import com.permutassep.config.Config;
import com.permutassep.interfaces.FirstLaunchCompleteListener;
import com.permutassep.model.Post;
import com.permutassep.presentation.interfaces.OnPostItemSelectedListener;
import com.permutassep.utils.PrefUtils;
import com.permutassep.utils.Utils;

public class ActivityMain extends BaseActivity
        implements
        OnPostItemSelectedListener,
        FragmentManager.OnBackStackChangedListener,
        FirstLaunchCompleteListener {

    @Override
    public void onBackStackChanged() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (PrefUtils.shouldReloadNewsFeed(this)) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentPagedNewsFeed()).commit();
            PrefUtils.markNewsFeedToReload(this, false);
        }

        if (f instanceof FragmentPagedNewsFeed) {
            setTitle(R.string.app_name);
            if (Utils.getUser(this) != null) {
//                result.setSelectionByIdentifier(DrawerItems.HOME.id, false);
            }
        } else if (f instanceof FragmentMyPosts) {
            setTitle(R.string.app_main_toolbar_title_my_posts);
            if (Utils.getUser(this) != null) {
//                result.setSelectionByIdentifier(DrawerItems.MY_POSTS.id, false);
            }
        } else if (f instanceof FragmentSearch) {
            setTitle(R.string.app_main_toolbar_title_search);
        } else if (f instanceof FragmentResult) {
            setTitle(R.string.app_main_toolbar_title_search_results);
        } else if (f instanceof FragmentCreatePost) {
            setTitle(R.string.app_main_toolbar_post_action);
        }

        invalidateOptionsMenu();

    }

    public enum DrawerItems {
        HOME(1000),
        MY_POSTS(1001),
        SETTINGS(1002);

        public int id;

        DrawerItems(int id) {
            this.id = id;
        }
    }

//    public Drawer.Result result;
//    private AccountHeader.Result headerResult = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if (Utils.getUser(this) != null) {

            final IProfile profile = new ProfileDrawerItem()
                    .withName(Utils.getUser(this).getName())
                    .withEmail(Utils.getUser(this).getEmail())
                    .withIcon(Uri.parse("https://graph.facebook.com/" + Utils.getUser(this).getSocialUserId() + "/picture?width=460&height=460"));

            // Create the AccountHeader
//            headerResult = new AccountHeader()
//                    .withActivity(this)
//                    .withHeaderBackground(R.drawable.header)
//                    .addProfiles(profile)
//                    .withSavedInstance(savedInstanceState)
//                    .build();

//            result = new Drawer()
//                    .withActivity(this)
//                    .withAccountHeader(headerResult)
//                    .withToolbar(toolbar)
//                    .addDrawerItems(
//                            new PrimaryDrawerItem().withName(getString(R.string.app_nav_drawer_1)).withIdentifier(DrawerItems.HOME.id).withIcon(GoogleMaterial.Icon.gmd_home),
//                            new PrimaryDrawerItem().withName(getString(R.string.app_nav_drawer_2)).withIdentifier(DrawerItems.MY_POSTS.id).withIcon(GoogleMaterial.Icon.gmd_content_paste)
////                            new DividerDrawerItem(),
////                            new PrimaryDrawerItem().withName(getString(R.string.app_nav_drawer_3)).withIdentifier(DrawerItems.SETTINGS.id).withIcon(GoogleMaterial.Icon.gmd_settings)
//                    )
//                    .withSelectedItem(0)
//                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem drawerItem) {
//                            if (drawerItem != null) {
//                                replaceFragment(drawerItem.getIdentifier());
//                            }
//                        }
//                    })
//                    .build();
////            result.getListView().setVerticalScrollBarEnabled(false);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentPagedNewsFeed()).commit();
    }

    private void showTOSDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.tos_dialog_text))
                .setPositiveButton(getString(R.string.tos_dialog_accept), dialogClickListener)
                .setNegativeButton(getString(R.string.tos_dialog_cancel), dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    // Save the state
                    getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE)
                            .edit()
                            .putBoolean("tos_accepted", false)
                            .commit();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    finish();
                    break;
            }
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (f instanceof FragmentPagedNewsFeed) {
            getMenuInflater().inflate(R.menu.main, menu);

            if (Utils.getUser(this) != null) {
                menu.findItem(R.id.action_post).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBarSize()).setVisible(true);
                menu.findItem(R.id.action_logout).setVisible(true);
            }

            menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            PrefUtils.clearApplicationPreferences(this);
            LoginManager.getInstance().logOut();
            startActivity(new Intent(ActivityMain.this, ActivityLoginSignUp.class));
            finish();
        } else if (item.getItemId() == R.id.action_about) {

            new LibsBuilder()
                    .withFields(R.string.class.getFields())
                    .withActivityTitle(getString(R.string.about_activity_title))
                    .withAboutIconShown(true)
                    .withAboutDescription(getString(R.string.about_activity_description))
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .start(this);
        } else {
            replaceFragment(item.getItemId());
        }
        return false;
    }

    @Override
    public void showPostDetail(Post post) {
        String backStackEntryName = null;
        if ((getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof FragmentPagedNewsFeed)) {
            backStackEntryName = "news_feed";
        } else if ((getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof FragmentMyPosts)) {
            backStackEntryName = "my_posts";
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, FragmentPostDetail.instance(new Gson().toJson(post))).addToBackStack(backStackEntryName).commit();

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.hide(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer));
//        ft.add(R.id.fragmentContainer, FragmentPostDetail.instance(new Gson().toJson(post))).addToBackStack(backStackEntryName).commit();

        clearDrawerSelection();
    }

    @Override
    public void onBackPressed() {
//        if (result != null && result.isDrawerOpen()) {
//            result.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public void onFirstLaunchComplete() {
        if (!PrefUtils.firstTimeDrawerOpened(this) && Utils.getUser(this) != null) {
//            result.openDrawer();
            PrefUtils.markFirstTimeDrawerOpened(this);
        }
    }

    public void replaceFragment(int id) {

        // Action Home
        if (id == DrawerItems.HOME.id) {
            getSupportFragmentManager().popBackStackImmediate("news_feed", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        // Action Post
        if (id == R.id.action_post) {
            clearDrawerSelection();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentCreatePost()).addToBackStack("news_feed").commit();
        }

        // Action Search
        if (id == R.id.action_search) {
            clearDrawerSelection();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentSearch()).addToBackStack("news_feed").commit();
        }
        // Action My posts
        if (id == DrawerItems.MY_POSTS.id) {
            getSupportFragmentManager().popBackStackImmediate("news_feed", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentMyPosts()).addToBackStack("news_feed").commit();
        }
    }

    private void clearDrawerSelection() {
        if (Utils.getUser(this) != null) {
//            result.setSelection(-1);
        }
    }
}
