package com.permutassep.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.model.AuthModel;
import com.permutassep.model.User;
import com.permutassep.rest.PermutasSEPRestClient;
import com.permutassep.utils.PrefUtils;
import com.permutassep.utils.Utils;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import java.util.Arrays;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivityLogin extends ActionBarActivity {

    private UiLifecycleHelper uiHelper;

    private EditText etNameOrUsername;
    private EditText etPassword;
    private TextView btnLogin;
    private ProgressDialog pDlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        setUI();
    }

    private void setUI() {

        etNameOrUsername = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Validate vName = new Validate(etNameOrUsername);
        vName.addValidator(new EmailValidator(getApplicationContext()));

        Validate vPassword = new Validate(etPassword);
        vPassword.addValidator(new NotEmptyValidator(getApplicationContext()));

        final Form f = new Form();
        f.addValidates(vName);
        f.addValidates(vPassword);

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.validate()) {
                    showDialog(getString(R.string.app_login_dlg_login_title), getString(R.string.app_login_dlg_login_logging_in));
                    new PermutasSEPRestClient().get().login(new AuthModel(etNameOrUsername.getText().toString(), etPassword.getText().toString()), new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {
                            hideDialog();
                            goToMainActivity();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideDialog();
                            Utils.showSimpleDialog(R.string.app_login_dlg_login_err_text, R.string.accept, ActivityLogin.this, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                    });
                }


            }
        });

        LoginButton authButton = (LoginButton) findViewById(R.id.btnFbLogin);
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
            showDialog(getString(R.string.app_login_dlg_login_title), getString(R.string.app_login_dlg_login_logging_in));
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser fbUser, Response response) {
                    if (fbUser != null) {

                        String email = fbUser.getProperty("email") != null ? fbUser.getProperty("email").toString() : "";
                        if(TextUtils.isEmpty(email)){
                            final EditText input = new EditText(ActivityLogin.this);
                            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLogin.this);
                            alert.setView(input);
                            alert.setTitle(R.string.app_login_fb_dlg_missing_email_title);
                            alert.setMessage(R.string.app_login_fb_dlg_missing_email_text);
                            alert.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    login(input.getText().toString());
                                }
                            });

                            alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Session session = Session.getActiveSession();
                                    session.closeAndClearTokenInformation();
                                    finish();
                                }
                            });
                            alert.show();
                        }else{
                            login(email);
                        }
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
        Intent i = new Intent().setClass(ActivityLogin.this, ActivityMain.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    private void login(String email){

        if (new EmailValidator(getApplicationContext()).isValid(email)){
            new PermutasSEPRestClient().get().login(new AuthModel(email, Config.TEM_PWD), new Callback<User>() {
                @Override
                public void success(User user, retrofit.client.Response response) {
                    ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                    complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                    complexPreferences.commit();
                    hideDialog();
                    goToMainActivity();
                }

                @Override
                public void failure(RetrofitError error) {
                    hideDialog();
                    Utils.showSimpleDialog(R.string.app_login_dlg_login_err_not_registered_text, R.string.accept, ActivityLogin.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Session session = Session.getActiveSession();
                            session.closeAndClearTokenInformation();
                            finish();
                        }
                    });
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), R.string.app_login_fb_dlg_wrong_email, Toast.LENGTH_LONG).show();
            Session session = Session.getActiveSession();
            session.closeAndClearTokenInformation();
            finish();
        }

    }
}
