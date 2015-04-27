package com.permutassep.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
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

import java.util.Arrays;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivitySignUp extends BaseActivity {

    private UiLifecycleHelper uiHelper;
    private TextView btnRegister;
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private ProgressDialog pDlg;
    private LoginButton authButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        setUI();
    }

    private void setUI() {

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Validate vName = new Validate(etName);
        vName.addValidator(new NotEmptyValidator(getApplicationContext()));

        Validate vEmail = new Validate(etEmail);
        vEmail.addValidator(new EmailValidator(getApplicationContext()));

        Validate vPhone = new Validate(etPhone);
        vPhone.addValidator(new PhoneValidator(getApplicationContext()));

        Validate vPassword = new Validate(etPassword);
        vPassword.addValidator(new NotEmptyValidator(getApplicationContext()));

        final Form f = new Form();
        f.addValidates(vName);
        f.addValidates(vEmail);
        f.addValidates(vPhone);
        f.addValidates(vPassword);

        btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (f.validate()) {

                    showDialog(getString(R.string.app_sign_up_log_reg_dlg_title), getString(R.string.app_sign_up_log_reg_dlg_text));

                    /*
                    *  Get all the fields for the user registration
                    * */
                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();
                    String phone = etPhone.getText().toString();
                    final User u = new User(name, email, phone, password);


                    /*
                    * * Validate if the user already exists
                    * */
                    new PermutasSEPRestClient().get().login(new AuthModel(u.getEmail(), u.getPassword()), new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {

                            hideDialog();
                            Utils.showSimpleDialog(R.string.app_login_sign_up_user_exist, R.string.accept, ActivitySignUp.this, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Session session = Session.getActiveSession();
                                    session.closeAndClearTokenInformation();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            /*
                            * * Perform the call to the REST service
                            * */
                            new PermutasSEPRestClient().get().newUser(u, new Callback<User>() {
                                @Override
                                public void success(User user, retrofit.client.Response response) {
                                    hideDialog();
                                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                                    user.setPassword(password);
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

        authButton = (LoginButton) findViewById(R.id.btnLogin);
        authButton.setReadPermissions(Arrays.asList("email"));
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            showDialog(getString(R.string.app_sign_up_log_reg_dlg_title), getString(R.string.app_sign_up_log_reg_dlg_text));
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser fbUser, Response response) {
                    if (fbUser != null) {

                        final String email = fbUser.getProperty("email") != null ? fbUser.getProperty("email").toString() : "";

                        Intent i = new Intent().setClass(ActivitySignUp.this, ActivityCompleteFbData.class);
                        i.putExtra("name", fbUser.getName());
                        i.putExtra("email", email);
                        i.putExtra("fbUserId", fbUser.getId());
                        startActivity(i);
                        finish();
                        hideDialog();
                    }
                }
            });
        } else if (state.isClosed()) {
            // TODO: What to do when the session state is closed?
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger| it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
//            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void showDialog(String title, String text) {
        pDlg = ProgressDialog.show(this, title, text, true);
    }

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }

    private void goToMainActivity() {
        Intent i = new Intent().setClass(ActivitySignUp.this, ActivityMain.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
