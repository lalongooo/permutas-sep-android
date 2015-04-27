package com.permutassep;

import android.support.v7.view.ActionMode;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by jorge.hernandez on 4/27/2015.
 */
public class BaseActivity extends android.support.v7.app.ActionBarActivity{

    @Override
    protected void onStart() {
        super.onStart();
        // Get tracker.
        Tracker t = ((PermutasSEPApplication) getApplication()).getTracker();
        // Set screen name.
        t.setScreenName(getClass().getName());
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
