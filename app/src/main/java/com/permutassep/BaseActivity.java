package com.permutassep;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by jorge.hernandez on 4/27/2015.
 */
public class BaseActivity extends android.support.v7.app.ActionBarActivity{

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
