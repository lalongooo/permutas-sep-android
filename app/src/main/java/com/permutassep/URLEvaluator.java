package com.permutassep;

import android.os.Bundle;

public class URLEvaluator  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null && getIntent().getData() != null){

            if(getIntent().getData().getPathSegments().size() > 0){
                // TODO: Evaluate URL paths

            }else{
                // TODO: Go to main activity
            }

        }

    }
}
