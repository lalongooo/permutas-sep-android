package com.permutassep;

import android.content.Intent;
import android.os.Bundle;

import com.permutassep.rest.permutassep.RestPaths;
import com.permutassep.ui.ActivityLoginSignUp;
import com.permutassep.ui.ActivityMain;
import com.permutassep.ui.ActivityNewPassword;
import com.permutassep.ui.ActivityNewPasswordCaptureEmail;
import com.permutassep.utils.Utils;

public class URLEvaluator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = null;

        if(getIntent() != null && getIntent().getData() != null){

            if(getIntent().getData().getPathSegments().size() == 2){

                String path = getIntent().getData().getPathSegments().get(0);
                String token = getIntent().getData().getPathSegments().get(1);

                if(path.equals(RestPaths.REST_PASSWORD)){
                    intent = new Intent(URLEvaluator.this, ActivityNewPassword.class);
                    intent.putExtra("token", token);
                }
            }else{
                if(getIntent().getData().getPathSegments().size() > 0 && getIntent().getData().getPathSegments().get(0).equals(RestPaths.REST_PASSWORD)){
                    intent = new Intent(URLEvaluator.this, ActivityNewPasswordCaptureEmail.class);
                    intent.putExtra("error", ActivityNewPasswordCaptureEmail.MALFORMED_URL);
                }
            }
        }else{
            intent = new Intent(URLEvaluator.this, Utils.getUser(this) == null ? ActivityLoginSignUp.class : ActivityMain.class);
        }

        if(intent == null){
            intent = new Intent(URLEvaluator.this, Utils.getUser(this) == null ? ActivityLoginSignUp.class : ActivityMain.class);
        }

        startActivity(intent);
        finish();
    }
}
