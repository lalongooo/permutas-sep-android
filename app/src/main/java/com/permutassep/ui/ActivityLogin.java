package com.permutassep.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;
import com.permutassep.PermutasSEPApplication;
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

import org.json.JSONException;
import org.json.JSONObject;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;

public class ActivityLogin extends BaseActivity {

    private EditText etNameOrUsername;
    private EditText etPassword;
    private TextView btnLogin;
    private ProgressDialog pDlg;

    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUI();
    }

    private void setUI() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btnFbLogin);
        loginButton.setReadPermissions("email");
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    // Get tracker.
                                    Tracker t = ((PermutasSEPApplication) getApplication()).getTracker();
                                    t.send(new HitBuilders.EventBuilder()
                                            .setCategory(getString(R.string.ga_event_category_ux))
                                            .setAction(getString(R.string.ga_event_action_click))
                                            .setLabel(getString(R.string.ga_fb_login_action_label_on_completed))
                                            .build());

                                    String email = object.getString("email") != null ? object.getString("email") : "";
                                    if (TextUtils.isEmpty(email)) {

                                        t = ((PermutasSEPApplication) getApplication()).getTracker();
                                        t.send(new HitBuilders.EventBuilder()
                                                .setCategory(getString(R.string.ga_event_category_ux))
                                                .setAction(getString(R.string.ga_event_action_click))
                                                .setLabel(getString(R.string.ga_fb_login_action_label_unauthorized_email))
                                                .build());

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
                                                LoginManager.getInstance().logOut();
                                                finish();
                                            }
                                        });
                                        alert.show();
                                    } else {
                                        t.send(new HitBuilders.EventBuilder()
                                                .setCategory(getString(R.string.ga_event_category_ux))
                                                .setAction(getString(R.string.ga_event_action_click))
                                                .setLabel(getString(R.string.ga_fb_login_action_label_authorized_email))
                                                .build());
                                        login(email);
                                    }
                                } catch (JSONException e) {
                                    //TODO: Add exception handling
                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


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
                            Tracker t = ((PermutasSEPApplication) getApplication()).getTracker();
                            t.send(new HitBuilders.EventBuilder()
                                    .setCategory(getString(R.string.ga_event_category_ux))
                                    .setAction(getString(R.string.ga_event_action_click))
                                    .setLabel(getString(R.string.ga_login_action_label_success))
                                    .build());
                            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getBaseContext(), Config.APP_PREFERENCES_NAME, MODE_PRIVATE);
                            complexPreferences.putObject(PrefUtils.PREF_USER_KEY, user);
                            complexPreferences.commit();
                            hideDialog();
                            goToMainActivity();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideDialog();
                            // Get tracker.
                            Tracker t = ((PermutasSEPApplication) getApplication()).getTracker();
                            t.send(new HitBuilders.EventBuilder()
                                    .setCategory(getString(R.string.ga_event_category_ux))
                                    .setAction(getString(R.string.ga_event_action_click))
                                    .setLabel(getString(R.string.ga_login_action_label_bad_userorpassword))
                                    .build());
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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


    private void login(String email) {

        if (new EmailValidator(getApplicationContext()).isValid(email)) {
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
                        }
                    });
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), R.string.app_login_fb_dlg_wrong_email, Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
