package com.permutassep.presentation.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.login.LoginManager;
import com.lalongooo.permutassep.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.permutassep.presentation.interfaces.FirstLaunchCompleteListener;
import com.permutassep.presentation.interfaces.FragmentMenuItemSelectedListener;
import com.permutassep.presentation.interfaces.LoginCompleteListener;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.ActivityComponent;
import com.permutassep.presentation.internal.di.components.DaggerActivityComponent;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.navigation.Navigator;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.HomeView;
import com.permutassep.presentation.view.fragment.FragmentLogin;
import com.permutassep.presentation.view.fragment.FragmentLoginSignUp;
import com.permutassep.presentation.view.fragment.FragmentPostList;
import com.permutassep.presentation.view.fragment.FragmentSignUp;

import javax.inject.Inject;

/**
 * Main application screen. This is the app entry point.
 */
public class ActivityMain extends BaseActivity
        implements HomeView,
        HasComponent<ActivityComponent>,
        FragmentPostList.PostListListener,
        Navigator.NavigationListener,
        FragmentManager.OnBackStackChangedListener,
        FragmentSignUp.FacebookSignUpListener,
        LoginCompleteListener,
        FirstLaunchCompleteListener,
        FragmentMenuItemSelectedListener {


    public static final int DRAWER_IDENTIFIER_HOME = 1;
    public static final int DRAWER_IDENTIFIER_MY_POSTS = 1;
    @Inject
    Toolbar toolbar;
    private Drawer drawer;
    private UserModel userModel;
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        this.initializeInjector();
        activityComponent.inject(this);

        userModel = PrefUtils.getUser(this);
        if (userModel != null) {
            navigator.navigateToPostList(this);
            renderDrawerOptions();
        } else {
            navigator.navigateToLoginSignUp(this, true);
        }
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * Methods from the {@link HomeView} interface
     */

    @Override
    public void renderDrawerOptions() {

        IProfile profile = new ProfileDrawerItem()
                .withName(userModel.getName())
                .withEmail(userModel.getEmail())
                .withIcon(Uri.parse("https://graph.facebook.com/" + userModel.getSocialUserId() + "/picture?width=460&height=460"));

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar)
                .withSelectedItem(0)
                .build();


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
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            }
        }
    }

    /**
     * Method from {@link FragmentPostList.PostListListener}
     */

    @Override
    public void onPostClicked(PostModel postModel) {
        navigator.navigateToPostDetails(this, postModel.getId());
    }

    /**
     * Method from {@link Navigator.NavigationListener}
     */

    @Override
    public void onNextFragment(Class c) {
        if (c == FragmentLogin.class) {
            navigator.navigateToLogin(this);
        }

        if (c == FragmentSignUp.class) {
            navigator.navigateToSignUp(this);
        }

        if (c == FragmentPostList.class) {
            navigator.navigateToPostList(this);
        }
    }

    /**
     * Method from {@link FragmentManager.OnBackStackChangedListener}
     */

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

    /**
     * Method from {@link FragmentSignUp.FacebookSignUpListener}
     */

    @Override
    public void onFacebookSignUp(Bundle bundle) {
        navigator.navigateToCompleteFbData(this, bundle);
    }

    /**
     * Method from the {@link LoginCompleteListener}
     */

    @Override
    public void onLoginComplete(UserModel userModel) {
        this.userModel = userModel;
        PrefUtils.putUser(this, userModel);
        renderDrawerOptions();
    }

    /**
     * Method from {@link FirstLaunchCompleteListener}
     */
    @Override
    public void onFirstLaunchComplete() {
        if (!PrefUtils.firstTimeDrawerOpened(this) && userModel != null) {
            PrefUtils.markFirstTimeDrawerOpened(this);
            drawer.openDrawer();
        }
    }

    /**
     * Method from {@link FragmentMenuItemSelectedListener}
     */
    @Override
    public void onMenuItemSelected(int menuId) {
        if (menuId == R.id.action_logout) {
            PrefUtils.clearApplicationPreferences(this);
            LoginManager.getInstance().logOut();
            navigator.navigateToLoginSignUp(this, false);
        }
    }
}