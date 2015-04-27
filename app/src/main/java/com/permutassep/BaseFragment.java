package com.permutassep;

import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class BaseFragment extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext()) == 0) {
            // Get tracker.
            Tracker t = ((PermutasSEPApplication) getActivity().getApplication()).getTracker();
            // Set screen name.
            t.setScreenName(getClass().getName());
            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}