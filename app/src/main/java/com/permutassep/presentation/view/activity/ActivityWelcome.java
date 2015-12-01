package com.permutassep.presentation.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lalongooo.permutassep.R;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.utils.PrefUtils;


public class ActivityWelcome extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_activity_welcome);

        findViewById(R.id.button_decline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker t = ((AndroidApplication) getApplication()).getTracker();
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_app_decline_tos))
                        .build());
                finish();
            }
        });

        findViewById(R.id.button_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker t = ((AndroidApplication) getApplication()).getTracker();
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_app_accept_tos))
                        .build());
                PrefUtils.markTosAccepted(ActivityWelcome.this);
                Intent intent = new Intent(ActivityWelcome.this, ActivityAppOverview.class);
                startActivity(intent);
                finish();
            }
        });

//        ((TextView)findViewById(R.id.google_maps_legal)).setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));
    }
}
