package com.permutassep.ui;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lalongooo.permutassep.BuildConfig;
import com.lalongooo.permutassep.R;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.interfaces.LoginCompleteListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.AuthenticationComponent;
import com.permutassep.presentation.internal.di.components.DaggerAuthenticationComponent;
import com.permutassep.presentation.internal.di.modules.AuthenticationModule;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.presenter.LoginPresenter;
import com.permutassep.presentation.view.LoginView;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentLogin extends BaseFragment implements LoginView {

    /**
     * UI elements
     */
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnFbLogin)
    LoginButton loginButton;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    @Inject
    LoginPresenter loginPresenter;

    private LoginCompleteListener loginCompleteListener;
    private MaterialDialog progressDialog;
    private AuthenticationComponent authenticationComponent;
    private CallbackManager callbackManager;

    /**
     * Empty constructor
     */
    public FragmentLogin() {
        super();
    }

    /**
     * A static method to create a new instance of the {@link FragmentLogin} class
     *
     * @return An instance of {@link FragmentLogin}
     */
    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        setUpFacebookLoginButton();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        return fragmentView;
    }

    private void setUpFacebookLoginButton() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoading();
                GraphRequest graphRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                hideLoading();
                                String email = null;

                                ((AndroidApplication) getActivity().getApplication())
                                        .getTracker()
                                        .send(new HitBuilders.EventBuilder()
                                                .setCategory(getString(R.string.ga_event_category_ux))
                                                .setAction(getString(R.string.ga_event_action_click))
                                                .setLabel(getString(R.string.ga_fb_login_action_label_on_completed))
                                                .build());

                                try {

                                    /**
                                     * If an error occurs while retrieving the users email,
                                     * then we'll show a dialog for the user to capture it.
                                     */
                                    email = jsonObject.getString("email");

                                } catch (JSONException e) {

                                    ((AndroidApplication) getActivity().getApplication())
                                            .getTracker()
                                            .send(new HitBuilders.EventBuilder()
                                                    .setCategory(getString(R.string.ga_event_category_ux))
                                                    .setAction(getString(R.string.ga_event_action_click))
                                                    .setLabel(getString(R.string.ga_fb_login_action_label_unauthorized_email))
                                                    .build());

                                    /**
                                     * Create an EditText to capture the missing user email
                                     */
                                    EditText input = new EditText(getActivity());
                                    float scale = getResources().getDisplayMetrics().density;
                                    int dpAsPixels = (int) (15 * scale + 0.5f);
                                    input.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

                                    /**
                                     * Create an AlertDialog with the previously EditText created for the user to capture
                                     * the missing email, add button event listeners ans show.
                                     */
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setView(input);
                                    alert.setCancelable(false);
                                    alert.setTitle(R.string.app_login_fb_dlg_missing_email_title);
                                    alert.setMessage(R.string.app_login_fb_dlg_missing_email_text);
                                    alert.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            EditText editText = (EditText) ((AlertDialog) dialog).getCurrentFocus();
                                            if (new EmailValidator(getActivity()).isValid(editText.getText().toString())) {
                                                initializeInjector(editText.getText().toString(), BuildConfig.com_permutassep_fb_login_dummy_password);
                                                loginPresenter.login();
                                            } else {
                                                LoginManager.getInstance().logOut();
                                                showError(getString(R.string.app_login_fb_dlg_wrong_email));
                                            }
                                        }
                                    });
                                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            LoginManager.getInstance().logOut();
                                            showError(getString(R.string.app_login_fb_dlg_on_cancel));
                                        }
                                    });
                                    alert.show();
                                }

                                if (email != null) {

                                    ((AndroidApplication) getActivity().getApplication())
                                            .getTracker()
                                            .send(new HitBuilders.EventBuilder()
                                                    .setCategory(getString(R.string.ga_event_category_ux))
                                                    .setAction(getString(R.string.ga_event_action_click))
                                                    .setLabel(getString(R.string.ga_fb_login_action_label_authorized_email))
                                                    .build());

                                    initializeInjector(email, BuildConfig.com_permutassep_fb_login_dummy_password);
                                    loginPresenter.login();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                ((AndroidApplication) getActivity().getApplication())
                        .getTracker()
                        .send(new HitBuilders.EventBuilder()
                                .setCategory(getString(R.string.ga_event_category_ux))
                                .setAction(getString(R.string.ga_event_action_click))
                                .setLabel(getString(R.string.ga_fb_login_action_label_on_canceled))
                                .build());
                showError(getString(R.string.app_login_fb_dlg_on_cancel));
            }

            @Override
            public void onError(FacebookException exception) {
                ((AndroidApplication) getActivity().getApplication())
                        .getTracker()
                        .send(new HitBuilders.EventBuilder()
                                .setCategory(getString(R.string.ga_event_category_ux))
                                .setAction(getString(R.string.ga_event_action_click))
                                .setLabel(getString(R.string.ga_fb_login_action_label_on_error))
                                .build());
                showError(getString(R.string.app_login_fb_dlg_on_error));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginCompleteListener = (LoginCompleteListener) getActivity();
    }

    @OnClick(R.id.btnLogin)
    void onBtnLoginClick() {
        /**
         * Add validations of the EditText's
         */
        Validate vName = new Validate(etName);
        vName.addValidator(new EmailValidator(getActivity()));

        Validate vPassword = new Validate(etPassword);
        vPassword.addValidator(new NotEmptyValidator(getActivity()));

        Form form = new Form();
        form.addValidates(vName, vPassword);

        if (form.isValid()) {
            String user = etName.getText().toString();
            String password = etPassword.getText().toString();
            initializeInjector(user, password);
            loginPresenter.login();
        }
    }

    @OnClick(R.id.tvForgotPassword)
    void onForgotPasswordClick() {

        ((AndroidApplication) getActivity().getApplication())
                .getTracker()
                .send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_login_action_reset_password))
                        .build());

        new MaterialDialog.Builder(getActivity())
                .title(R.string.password_reset_dlg_title)
                .content(R.string.password_reset_dlg_msg)
                .inputRangeRes(6, 200, R.color.md_red_100)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .input(R.string.password_reset_dlg_input_hint, 0, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        initializeInjector(input.toString(), BuildConfig.com_permutassep_fb_login_dummy_password);
                        loginPresenter.validateEmail(input.toString());
                    }
                }).show();
    }

    private void initializeInjector(String user, String password) {
        this.authenticationComponent = DaggerAuthenticationComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .authenticationModule(new AuthenticationModule(user, password))
                .build();
        this.authenticationComponent.inject(this);
        this.loginPresenter.setView(this);
    }

    /**
     * Methods from the {@link LoginView} interface
     */

    @Override
    public void authorizeUser(UserModel userModel) {

        Tracker t = ((AndroidApplication) getActivity().getApplication()).getTracker();
        t.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.ga_event_category_ux))
                .setAction(getString(R.string.ga_event_action_click))
                .setLabel(getString(R.string.ga_login_action_label_success))
                .build());

        this.hideKeyboard();
        getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.navigationListener.onNextFragment(FragmentPagedNewsFeed.class);
        this.loginCompleteListener.onLoginComplete(userModel);
    }

    @Override
    public void showLoading() {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.app_login_dlg_login_title)
                .content(R.string.app_login_dlg_login_logging_in)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .show();
    }

    @Override
    public void notRegisteredUser() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.password_reset_dlg_title)
                .content(R.string.new_password_capture_email_wrong)
                .positiveText(R.string.accept)
                .show();
    }

    @Override
    public void showLoadingValidateUser() {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.please_wait)
                .content(R.string.new_password_capture_email_loading)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();
    }

    @Override
    public void performPasswordReset(String email) {
        this.loginPresenter.resetPassword(email);
    }

    @Override
    public void passwordResetRequestSent(String email) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.password_reset_dlg_title)
                .content(String.format(getString(R.string.password_reset_email_sent_msg), email))
                .positiveText(R.string.accept)
                .show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

        Tracker t = ((AndroidApplication) getActivity().getApplication()).getTracker();
        t.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.ga_event_category_ux))
                .setAction(getString(R.string.ga_event_action_click))
                .setLabel(getString(R.string.ga_login_action_label_bad_userorpassword))
                .build());

        LoginManager.getInstance().logOut();
        new MaterialDialog.Builder(getActivity())
                .title(R.string.app_login_dlg_login_title)
                .content(message)
                .positiveText(R.string.accept)
                .show();
    }
}
