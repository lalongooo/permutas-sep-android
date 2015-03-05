package com.permutassep.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.permutassep.R;
import com.permutassep.adapter.AppOverviewAdapter;
import com.permutassep.config.Config;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.Arrays;

public class ActivityAppOverview extends Activity{

    private PageIndicator indicator;
    private ViewPager viewPager;
    private UiLifecycleHelper uiHelper;
    int backgroundColors[] = {0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_app_overview);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        setUI();
    }

    private void setUI() {

        ColorDrawable[] colors = {new ColorDrawable(backgroundColors[3]), new ColorDrawable(backgroundColors[0])};
        TransitionDrawable trans = new TransitionDrawable(colors);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setBackgroundDrawable(trans);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        AppOverviewAdapter adapter = new AppOverviewAdapter(this);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        indicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int previousPosition = 1;
                ColorDrawable[] colors = {new ColorDrawable(backgroundColors[previousPosition]), new ColorDrawable(backgroundColors[position])};
                TransitionDrawable trans = new TransitionDrawable(colors);
                viewPager.setBackgroundDrawable(trans);
                trans.startTransition(2000);
                previousPosition = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        LoginButton authButton = (LoginButton) findViewById(R.id.btnLogin);
        authButton.setReadPermissions(Arrays.asList("email"));
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Display the parsed user info
                        Intent i = new Intent().setClass(ActivityAppOverview.this, ActivityCreatePost.class);
                        getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE).edit()
                                .putString("name", user.getName()).commit();
                        getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE).edit()
                                .putString("email", user.getProperty("email") != null ? user.getProperty("email").toString() : "").commit();
                        startActivity(i);
                        finish();
                    }
                }
            });
        } else if (state.isClosed()) {
            // TODO: What to do when the session state is closed?
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger| it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
//            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
