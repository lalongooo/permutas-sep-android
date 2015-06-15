package com.permutassep;

import android.content.Intent;
import android.os.Bundle;

import com.permutassep.rest.permutassep.RestPaths;
import com.permutassep.ui.ActivityLoginSignUp;
import com.permutassep.ui.ActivityNewPassword;

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
            }
        }else{
            intent = new Intent(URLEvaluator.this, ActivityLoginSignUp.class);
        }

        if(intent == null){
            intent = new Intent(URLEvaluator.this, ActivityLoginSignUp.class);
        }

        startActivity(intent);
        finish();
    }
}
