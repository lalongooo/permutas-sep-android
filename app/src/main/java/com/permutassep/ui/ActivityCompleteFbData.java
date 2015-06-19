package com.permutassep.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.lalongooo.permutassep.BuildConfig;
import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.config.Config;
import com.permutassep.model.AuthModel;
import com.permutassep.model.User;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.PrefUtils;
import com.permutassep.utils.Utils;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivityCompleteFbData extends BaseActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private ProgressDialog pDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_fb_data);

        TextView btnContinueRegistration = (TextView) findViewById(R.id.btnContinueRegistration);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String email = extras.getString("email");

        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setFocusable(false);
        }

        if (!TextUtils.isEmpty(email)) {
            etEmail.setText(email);
            etEmail.setFocusable(false);
        }

        Validate vName = new Validate(etName);
        vName.addValidator(new NotEmptyValidator(getApplicationContext()));

        Validate vEmail = new Validate(etEmail);
        vEmail.addValidator(new EmailValidator(getApplicationContext()));

        Validate vPhone = new Validate(etPhone);
        vPhone.addValidator(new PhoneValidator(getApplicationContext()));

        final Form f = new Form();
        f.addValidates(vName);
        f.addValidates(vEmail);
        f.addValidates(vPhone);

        btnContinueRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f.validate()){
                    showDialog(getString(R.string.app_sign_up_log_reg_dlg_title), getString(R.string.app_sign_up_log_reg_dlg_text));
                    new PermutasSEPRestClient().get().login(new AuthModel(etEmail.getText().toString(), BuildConfig.com_permutassep_fb_login_dummy_password), new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {
                            hideDialog();
                            LoginManager.getInstance().logOut();
                            Utils.showSimpleDialog(R.string.app_login_sign_up_user_exist, R.string.accept, ActivityCompleteFbData.this, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            User u = new User();
                            u.setName(etName.getText().toString());
                            u.setEmail(etEmail.getText().toString());
                            u.setPhone(etPhone.getText().toString());
                            u.setSocialUserId(getIntent().getExtras().getString("fbUserId"));
                            u.setPassword(BuildConfig.com_permutassep_fb_login_dummy_password);

                            new PermutasSEPRestClient().get().newUser(u, new Callback<User>() {

                                @Override
                                public void success(User user, retrofit.client.Response response) {
                                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                                    user.setPassword(BuildConfig.com_permutassep_fb_login_dummy_password);
                                    complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                                    complexPreferences.putObject(PrefUtils.PREF_ORIGINAL_USER_KEY, user);
                                    complexPreferences.commit();

                                    PrefUtils.setNormalUser(getApplicationContext(), true);
                                    hideDialog();
                                    goToMainActivity();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    // TODO: Add an error message dialog
                                    hideDialog();
                                }
                            });

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

    private void goToMainActivity() {
        Intent i = new Intent().setClass(ActivityCompleteFbData.this, ActivityMain.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginManager.getInstance().logOut();
    }
}
