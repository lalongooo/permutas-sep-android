package com.permutassep;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.permutassep.ui.ActivityWelcome;
import com.permutassep.utils.PrefUtils;

/**
 * Created by jorge.hernandez on 4/27/2015.
 */
public class BaseActivity extends android.support.v7.app.AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PrefUtils.isTosAccepted(this)) {
            Intent intent = new Intent(this, ActivityWelcome.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == 0) {
            // Get tracker.
            Tracker t = ((PermutasSEPApplication) getApplication()).getTracker();
            // Set screen name.
            t.setScreenName(getClass().getName());
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}
