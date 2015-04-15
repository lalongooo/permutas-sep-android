package com.permutassep.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.model.User;
import com.permutassep.rest.PermutasSEPRestClient;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;

import java.util.Arrays;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivitySignUp extends Activity {

    private UiLifecycleHelper uiHelper;
    private Button btnRegister;
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private ProgressDialog pDlg;


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

        btnRegister = (Button) findViewById(R.id.btnRegister);
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
                    User u = new User(name, email, phone, password);

                    /*
                    * Perform the call to the REST service
                    * */
                    new PermutasSEPRestClient().get().newUser(u, new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {
                            hideDialog();
                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                            user.setPassword(password);
                            complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                            complexPreferences.commit();

                            PrefUtils.setNormalUser(getApplicationContext(), true);
                            goToMainActivity();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideDialog();
                        }
                    });
                }
            }
        });

        LoginButton authButton = (LoginButton) findViewById(R.id.btnLogin);
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
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser fbUser, Response response) {
                    if (fbUser != null) {

                        final String email = fbUser.getProperty("email") != null ? fbUser.getProperty("email").toString() : "";
                        final String fbUserId = fbUser.getId();

                        // TODO: Is very important to change this code. Due to deadlines, we need to make it this way :(
                        User u;
                        if (email != "") {
                            u = new User(fbUser.getName(), email, "0000000000", fbUser.getId());
                        } else {
                            u = new User(fbUser.getName(), fbUser.getId().concat("@facebook.com"), "0000000000", "facebook");
                        }

                        /*
                        * * Perform the call to the REST service
                        * */
                        new PermutasSEPRestClient().get().newUser(u, new Callback<User>() {
                            @Override
                            public void success(User user, retrofit.client.Response response) {
                                hideDialog();
                                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);

                                if (email != "") {
                                    user.setPassword(fbUserId);
                                } else {
                                    user.setPassword("facebook");
                                }

                                complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                                complexPreferences.commit();

                                PrefUtils.setNormalUser(getApplicationContext(), true);
                                goToMainActivity();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                hideDialog();
                            }
                        });
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
