package com.permutassep.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.lalongooo.permutassep.R;
import com.permutassep.utils.PrefUtils;


public class ActivityWelcome extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        findViewById(R.id.button_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.markTosAccepted(ActivityWelcome.this);
                Intent intent = new Intent(ActivityWelcome.this, ActivityAppOverview.class);
                startActivity(intent);
                finish();
            }
        });

        ((TextView)findViewById(R.id.google_maps_legal)).setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));

        findViewById(R.id.button_decline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
