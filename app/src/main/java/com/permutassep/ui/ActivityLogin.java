package com.permutassep.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.permutassep.R;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

public class ActivityLogin extends Activity {

    private EditText etNameOrUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUI();
    }

    private void setUI() {

        etNameOrUsername = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Validate vName = new Validate(etNameOrUsername);
        vName.addValidator(new NotEmptyValidator(getApplicationContext()));

        Validate vPassword = new Validate(etPassword);
        vPassword.addValidator(new NotEmptyValidator(getApplicationContext()));

        final Form f = new Form();
        f.addValidates(vName);
        f.addValidates(vPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.validate()) {
                    // TODO: Add login method to the REST service
                }
            }
        });



    }

    private void goToMainActivity() {
        Intent i = new Intent().setClass(ActivityLogin.this, ActivityMain.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
