package com.permutassep;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.permutassep.presentation.config.Config;
import com.permutassep.ui.ActivityMain;
import com.permutassep.ui.FragmentNewPassword;

public class IntentEvaluator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = null;

        if (getIntent() != null && getIntent().getData() != null) {

            if (getIntent().getData().getPathSegments().size() == 1) {

                String path = getIntent().getData().getPathSegments().get(0);
                String token = getIntent().getData().getQueryParameter(Config.PWD_RESET_TOKEY_KEY);
                String email = getIntent().getData().getQueryParameter(Config.PWD_RESET_EMAIL_KEY);

                if (path.equals(Config.REST_PASSWORD) && token != null && email != null) {
                    intent = new Intent(IntentEvaluator.this, ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Config.PWD_RESET_TOKEY_KEY, token);
                    intent.putExtra(Config.PWD_RESET_EMAIL_KEY, email);
                    intent.putExtra(FragmentNewPassword.EXTRA_RESET_PASSWORD, true);
                }
            } else {
                if (getIntent().getData().getPathSegments().size() > 0 && getIntent().getData().getPathSegments().get(0).equals(Config.REST_PASSWORD)) {
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