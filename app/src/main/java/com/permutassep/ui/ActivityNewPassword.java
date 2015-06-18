package com.permutassep.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.config.Config;
import com.permutassep.model.AuthModel;
import com.permutassep.model.ConfirmPasswordReset;
import com.permutassep.model.User;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.PrefUtils;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityNewPassword extends BaseActivity {

    private EditText etPasswordOne;
    private EditText etPasswordTwo;
    private ProgressDialog pDlg;
    private TextView tvLabel;
    private TextView btnResetPassword;

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

        etPasswordOne = (EditText) findViewById(R.id.etPasswordOne);
        etPasswordTwo = (EditText) findViewById(R.id.etPasswordTwo);
        tvLabel = (TextView) findViewById(R.id.tvLabel);

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

                                        new PermutasSEPRestClient().get().login(new AuthModel(getIntent().getStringExtra(Config.PWD_RESET_EMAIL_KEY), etPasswordOne.getText().toString()), new Callback<User>() {
                                            @Override
                                            public void success(User user, retrofit.client.Response response) {
                                                hideDialog();
                                                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                                                complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                                                complexPreferences.commit();

                                                Intent i = new Intent().setClass(ActivityNewPassword.this, ActivityMain.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                hideDialog();
                                                new AlertDialog.Builder(ActivityNewPassword.this)
                                                        .setTitle(R.string.error)
                                                        .setMessage(R.string.new_password_login_error)
                                                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                startActivity(new Intent(ActivityNewPassword.this, ActivityLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                                                finish();
                                                            }
                                                        })
                                                        .show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        hideDialog();
                                        etPasswordOne.setText("");
                                        etPasswordTwo.setText("");
                                        tvLabel.setTextColor(getResources().getColor(R.color.error_message));
                                        tvLabel.setText(R.string.new_password_capure_outdated_url);
                                        btnResetPassword.setText(R.string.new_password_request_new_token);
                                        btnResetPassword.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i = new Intent().setClass(ActivityNewPassword.this, ActivityNewPasswordCaptureEmail.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();
                                            }
                                        });

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
