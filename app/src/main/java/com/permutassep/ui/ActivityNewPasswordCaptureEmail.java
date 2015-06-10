package com.permutassep.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;

public class ActivityNewPasswordCaptureEmail extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_capture_email);

        // Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setTitle(R.string.new_password_toolbar_title);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);


        TextView btnResetPassword = (TextView) findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent().setClass(ActivityNewPasswordCaptureEmail.this, ActivityNewPasswordEmailSent.class).putExtra("email", etEmail.getText().toString()));
                finish();
            }
        });
    }

}
