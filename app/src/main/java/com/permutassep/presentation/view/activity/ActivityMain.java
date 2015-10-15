package com.permutassep.presentation.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.lalongooo.permutassep.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.permutassep.model.User;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.ActivityComponent;
import com.permutassep.presentation.internal.di.components.DaggerActivityComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.navigation.Navigator;
import com.permutassep.presentation.view.HomeView;
import com.permutassep.presentation.view.fragment.FragmentLogin;
import com.permutassep.presentation.view.fragment.FragmentLoginSignUp;
import com.permutassep.presentation.view.fragment.FragmentPostList;
import com.permutassep.presentation.view.fragment.FragmentSignUp;
import com.permutassep.utils.Utils;

import javax.inject.Inject;

/**
 * Main application screen. This is the app entry point.
 */
public class ActivityMain extends BaseActivity
        implements HomeView,
        HasComponent<ActivityComponent>,
        FragmentPostList.PostListListener,
        Navigator.NavigationListener,
        FragmentManager.OnBackStackChangedListener {


    public static final int DRAWER_IDENTIFIER_HOME = 1;
    public static final int DRAWER_IDENTIFIER_MY_POSTS = 1;

    @Inject
    Drawer drawer;

    @Inject
    Toolbar toolbar;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        this.initializeInjector();
        activityComponent.inject(this);

        renderDrawerOptions();

        User user = Utils.getUser(this);
        if (user != null) {
            navigator.navigateToPostList(this);
        } else {
            navigator.navigateToLoginSignUp(this);
        }
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        renderToolbarOptions(menu);
        return true;
    }

    /**
     * Methods from the {@link HomeView} interface
     */

    @SuppressWarnings("deprecation")
    @Override
    public void renderToolbarOptions(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        if (Utils.getUser(this) != null) {
            menu.findItem(R.id.action_post).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBarSize()).setVisible(true);
            menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());
            menu.findItem(R.id.action_logout).setVisible(true);
        }

        menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_search).color(Color.WHITE).actionBarSize());
    }

    @Override
    public void renderDrawerOptions() {
        drawer.addItems(
                new PrimaryDrawerItem()
                        .withName(getString(R.string.app_nav_drawer_1))
                        .withIdentifier(ActivityMain.DRAWER_IDENTIFIER_HOME)
                        .withIcon(GoogleMaterial.Icon.gmd_home),

                new PrimaryDrawerItem()
                        .withName(getString(R.string.app_nav_drawer_2))
                        .withIdentifier(ActivityMain.DRAWER_IDENTIFIER_MY_POSTS)
                        .withIcon(GoogleMaterial.Icon.gmd_content_paste)
        );

        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                return false;
            }
        });
    }

    @Override
    public void onDrawerItemSelected(int drawerItemId) {

    }

    @Override
    public void onMenuOptionItemSelected(int menuItemId) {

    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    @Override
    public void onPostClicked(PostModel postModel) {
        navigator.navigateToPostDetails(this, postModel.getId());
    }

    @Override
    public void onNextFragment(Class c) {
        if (c == FragmentLogin.class) {
            navigator.navigateToLogin(this);
        }

        if (c == FragmentSignUp.class) {
            navigator.navigateToSignup(this);
        }

        if (c == FragmentPostList.class) {
            navigator.navigateToPostList(this);
        }
    }

    @Override
    public void onBackStackChanged() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (f instanceof FragmentLoginSignUp) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }
}
