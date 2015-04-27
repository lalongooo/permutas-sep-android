package com.permutassep;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

public class PermutasSEPApplication extends Application {

    private static final String PROPERTY_ID = "UA-62325816-2";

    public PermutasSEPApplication() {
        super();
    }

    synchronized Tracker getTracker() {

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        Tracker t = analytics.newTracker(PROPERTY_ID);
        t.enableAdvertisingIdCollection(true);
        return t;
    }
}