package com.permutassep.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.config.Config;
import com.permutassep.model.ConfirmPasswordReset;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityNewPassword extends BaseActivity {

    private EditText etPasswordOne;
    private EditText etPasswordTwo;
    private ProgressDialog pDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setTitle(R.string.new_password_toolbar_title);

        setUI();
    }

    private void setUI() {

        TextView btnResetPassword;
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

        btnResetPassword = (TextView) findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.validate()) {
                    if (etPasswordOne.getText().toString().equals(etPasswordTwo.getText().toString())) {

                        showDialog(getString(R.string.please_wait), getString(R.string.new_password_resetting_password));
                        new PermutasSEPRestClient().get().confirmPasswordReset(
                                new ConfirmPasswordReset(
                                        getIntent().getStringExtra("token")
                                        , etPasswordOne.getText().toString()
                                        , etPasswordTwo.getText().toString()
                                )
                                , new ResponseCallback() {
                                    @Override
                                    public void success(Response response) {
                                        hideDialog();
                                        Intent i = new Intent().setClass(ActivityNewPassword.this, ActivityMain.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        hideDialog();
                                    }
                                });

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.new_password_passwords_mismatch, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showDialog(String title, String text) {
        pDlg = ProgressDialog.show(this, title, text, true);
    }

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }
}
