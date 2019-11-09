package com.permutassep.ui;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lalongooo.permutassep.R;
import com.permutassep.presentation.interfaces.LoginCompleteListener;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.AuthenticationComponent;
import com.permutassep.presentation.internal.di.components.DaggerAuthenticationComponent;
import com.permutassep.presentation.internal.di.modules.AuthenticationModule;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.presenter.SignUpPresenter;
import com.permutassep.presentation.view.SignUpView;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSignUp extends BaseFragment implements SignUpView {

    /**
     * UI elements
     */
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    LoginButton loginButton;
    @Inject
    SignUpPresenter signUpPresenter;

    private LoginCompleteListener loginCompleteListener;
    private MaterialDialog progressDialog;
    private AuthenticationComponent authenticationComponent;
    private CallbackManager callbackManager;
    private FacebookSignUpListener facebookSignUpListener;

    /**
     * A static method to create a new instance of the {@link FragmentSignUp} class
     *
     * @return An instance of {@link FragmentSignUp}
     */
    public static FragmentSignUp newInstance() {
        return new FragmentSignUp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_signup, container, false);
        ButterKnife.bind(this, fragmentView);
        setUpFacebookLoginButton();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        facebookSignUpListener = (FacebookSignUpListener) getActivity();
        loginCompleteListener = (LoginCompleteListener) getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpFacebookLoginButton() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoading();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                hideLoading();
                                String email;

                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    email = null;
                                }

                                Bundle bundle = new Bundle();
                                bundle.putString("name", Profile.getCurrentProfile().getName());
                                bundle.putString("email", email);
                                bundle.putString("socialUserId", Profile.getCurrentProfile().getId());
                                facebookSignUpListener.onFacebookSignUp(bundle);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }

    @OnClick(R.id.btnRegister)
    void onBtnLoginClick() {
        /**
         * Add validations of the EditText's
         */
        Validate vName = new Validate(etName);
        vName.addValidator(new NotEmptyValidator(getActivity()));

        Validate vEmail = new Validate(etEmail);
        vEmail.addValidator(new EmailValidator(getActivity()));

        Validate vPhone = new Validate(etPhone);
        vPhone.addValidator(new PhoneValidator(getActivity()));

        Validate vPassword = new Validate(etPassword);
        vPassword.addValidator(new NotEmptyValidator(getActivity()));

        Form form = new Form();
        form.addValidates(vName, vEmail, vPhone, vPassword);

        if (form.isValid()) {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            initializeInjector(name, email, phone, password);
            signUpPresenter.signUp();
        }
    }

    private void initializeInjector(String name, String email, String phone, String password) {

        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);

        this.authenticationComponent = DaggerAuthenticationComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .authenticationModule(new AuthenticationModule(user))
                .build();
        authenticationComponent.inject(this);
        signUpPresenter.setView(this);
    }

    /**
     * Methods from the {@link SignUpView} interface
     */

    @Override
    public void signedUpUser(UserModel userModel) {
        this.hideKeyboard();
        this.loginCompleteListener.onLoginComplete(userModel);
        getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.navigationListener.onNextFragment(FragmentPagedNewsFeed.class);
    }

    @Override
    public void showDuplicateEmailErrorMessage() {
        showError(getString(R.string.app_sign_up_error_duplicate_email));
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.please_wait)
                    .content(R.string.app_sign_up_log_reg_dlg_text)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
        } else {
            progressDialog.show();
            ;
        }
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
        this.showToastMessage(message);
    }

    public interface FacebookSignUpListener {
        void onFacebookSignUp(Bundle bundle);
    }
}