package com.permutassep;

import android.content.Intent;
import android.os.Bundle;

import com.permutassep.config.Config;
import com.permutassep.rest.permutassep.RestPaths;
import com.permutassep.ui.ActivityLoginSignUp;
import com.permutassep.ui.ActivityMain;
import com.permutassep.ui.ActivityNewPassword;
import com.permutassep.ui.ActivityNewPasswordCaptureEmail;
import com.permutassep.utils.Utils;

public class IntentEvaluator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = null;

        if(getIntent() != null && getIntent().getData() != null){

            if(getIntent().getData().getPathSegments().size() == 1){

                String path = getIntent().getData().getPathSegments().get(0);
                String token = getIntent().getData().getQueryParameter(Config.PWD_RESET_TOKEY_KEY);
                String email = getIntent().getData().getQueryParameter(Config.PWD_RESET_EMAIL_KEY);

                if(path.equals(RestPaths.REST_PASSWORD) && token != null && email != null){
                    intent = new Intent(IntentEvaluator.this, ActivityNewPassword.class);
                    intent.putExtra(Config.PWD_RESET_TOKEY_KEY, token);
                    intent.putExtra(Config.PWD_RESET_EMAIL_KEY, email);
                }
            }else{
                if(getIntent().getData().getPathSegments().size() > 0 && getIntent().getData().getPathSegments().get(0).equals(RestPaths.REST_PASSWORD)){
                    intent = new Intent(IntentEvaluator.this, ActivityNewPasswordCaptureEmail.class);
                    intent.putExtra("error", ActivityNewPasswordCaptureEmail.MALFORMED_URL);
                }
            }
        }

        if(intent == null){
            intent = new Intent(IntentEvaluator.this, Utils.getUser(this) == null ? ActivityLoginSignUp.class : ActivityMain.class);
        }

        startActivity(intent);
        finish();
    }
}
