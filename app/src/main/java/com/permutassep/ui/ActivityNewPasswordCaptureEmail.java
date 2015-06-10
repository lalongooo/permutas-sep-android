package com.permutassep.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.model.Email;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;

import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityNewPasswordCaptureEmail extends BaseActivity {

    private ProgressDialog pDlg;

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
        final Form f = new Form();

        Validate vName = new Validate(etEmail);
        vName.addValidator(new EmailValidator(this));
        f.addValidates(vName);

        TextView btnResetPassword = (TextView) findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.validate()) {
                    showDialog(getString(R.string.please_wait), getString(R.string.new_password_capure_email_loading));
                    new PermutasSEPRestClient().get().resetPassword(new Email(etEmail.getText().toString()), new ResponseCallback() {
                        @Override
                        public void success(Response response) {
                            hideDialog();
                            startActivity(new Intent().setClass(ActivityNewPasswordCaptureEmail.this, ActivityNewPasswordEmailSent.class).putExtra("email", etEmail.getText().toString()));
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            TextView tvLabel = (TextView) findViewById(R.id.tvLabel);
                            tvLabel.setTextColor(getResources().getColor(R.color.error_message));
                            tvLabel.setText(R.string.new_password_capure_email_wrong);
                            hideDialog();
                        }
                    });
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
