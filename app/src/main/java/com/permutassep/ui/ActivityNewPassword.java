package com.permutassep.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.config.Config;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

public class ActivityNewPassword extends BaseActivity {

    private Toolbar toolbar;
    private EditText etPasswordOne;
    private EditText etPasswordTwo;
    private TextView btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        toolbar= (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setTitle(R.string.new_password_toolbar_title);

        setUI();

        for (String param : getIntent().getData().getQueryParameterNames()){
            Log.d(param, getIntent().getData().getQueryParameter(param));
        }
    }

    private void setUI() {

        etPasswordOne = (EditText) findViewById(R.id.etPasswordOne);
        etPasswordTwo = (EditText) findViewById(R.id.etPasswordTwo);

        Validate vPasswordOne = new Validate(etPasswordOne);
        vPasswordOne.addValidator(new NotEmptyValidator(getApplicationContext()));
        vPasswordOne.addValidator(new LengthValidator(getApplicationContext(), R.string.new_password_password_length_error, Config.PASSWORD_MIN_LENGTH, Config.PASSWORD_MAX_LENGTH));

        Validate vPasswordTwo = new Validate(etPasswordTwo);
        vPasswordTwo.addValidator(new NotEmptyValidator(getApplicationContext()));
        vPasswordTwo.addValidator(new LengthValidator(getApplicationContext(), R.string.new_password_password_length_error, Config.PASSWORD_MIN_LENGTH, Config.PASSWORD_MAX_LENGTH));

        final Form f = new Form();
        f.addValidates(vPasswordOne);
        f.addValidates(vPasswordTwo);

        btnResetPassword = (TextView)findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.validate()) {
                    if (etPasswordOne.getText().toString().equals(etPasswordTwo.getText().toString())) {
                        Intent i = new Intent().setClass(ActivityNewPassword.this, ActivityMain.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.new_password_passwords_mismatch, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
