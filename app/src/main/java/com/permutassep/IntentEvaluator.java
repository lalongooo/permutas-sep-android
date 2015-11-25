package com.permutassep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.permutassep.config.Config;
import com.permutassep.presentation.view.activity.ActivityMain;
import com.permutassep.presentation.view.activity.ActivityNewPassword;
import com.permutassep.rest.permutassep.RestPaths;

public class IntentEvaluator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = null;

        if (getIntent() != null && getIntent().getData() != null) {
            Log.i("getIntent()", "NOT NULL");
            Log.i("getIntent().getData()", "NOT NULL");

            if (getIntent().getData().getPathSegments().size() == 1) {

                Log.i("getPathSegments().size", "Equals to 1");

                String path = getIntent().getData().getPathSegments().get(0);
                String token = getIntent().getData().getQueryParameter(Config.PWD_RESET_TOKEY_KEY);
                String email = getIntent().getData().getQueryParameter(Config.PWD_RESET_EMAIL_KEY);

                if (path.equals(RestPaths.REST_PASSWORD) && token != null && email != null) {
                    intent = new Intent(IntentEvaluator.this, ActivityNewPassword.class);
                    intent.putExtra(Config.PWD_RESET_TOKEY_KEY, token);
                    intent.putExtra(Config.PWD_RESET_EMAIL_KEY, email);
                }
            } else {
                if (getIntent().getData().getPathSegments().size() > 0 && getIntent().getData().getPathSegments().get(0).equals(RestPaths.REST_PASSWORD)) {
                    intent = new Intent(IntentEvaluator.this, ActivityMain.class);
                }
            }
        }

        if (intent == null) {
            intent = new Intent(IntentEvaluator.this, ActivityMain.class);
        }

        startActivity(intent);
        finish();
    }
}