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
import com.permutassep.utils.Utils;

import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivityLoginSignUp extends Activity {

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
        setContentView(R.layout.activity_login_signup);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        setUI();
    }

    private void setUI() {

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.login_sign_up_log_reg_dlg_title), getString(R.string.login_sign_up_log_reg_dlg_text));

                String name = etName.getText().toString();
                String email= etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String phone =  etPhone.getText().toString();

                User u = new User(name, email, phone, password);

                if(!name.isEmpty() && Utils.isValidEmail(email) && !password.isEmpty()){
                    PermutasSEPRestClient.get().newUser(u, new Callback<User>() {
                        @Override
                        public void success(User user, retrofit.client.Response response) {
                            hideDialog();
                            goToLoginMActivity();
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
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Display the parsed user info
                        Intent i = new Intent().setClass(ActivityLoginSignUp.this, ActivityMain.class);
                        getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE).edit()
                                .putString("name", user.getName()).commit();
                        getSharedPreferences(Config.APP_PREFERENCES_NAME, MODE_PRIVATE).edit()
                                .putString("email", user.getProperty("email") != null ? user.getProperty("email").toString() : "").commit();
                        startActivity(i);
                        finish();
                    }
                }
            });
        } else if (state.isClosed()) {
            // TODO: What to do when the session state is closed?
        }
    }

    private void goToNextActivity(){
        Intent i = new Intent().setClass(ActivityLoginSignUp.this, ActivityLoginSignUp.class);
        startActivity(i);
        finish();
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
        if(pDlg != null)
            pDlg.dismiss();
    }

    private void goToLoginMActivity(){
        Intent i = new Intent().setClass(ActivityLoginSignUp.this, ActivityMain.class);
        startActivity(i);
        finish();
    }
}