package com.permutassep.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentSignupBinding;
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

public class FragmentSignUp extends BaseFragment implements SignUpView {

    private CaFragmentSignupBinding binding;

    @Inject
    SignUpPresenter signUpPresenter;

    private LoginCompleteListener loginCompleteListener;
    private MaterialDialog progressDialog;
    private AuthenticationComponent authenticationComponent;
    private CallbackManager callbackManager;
    private FacebookSignUpListener facebookSignUpListener;

    public static FragmentSignUp newInstance() {
        return new FragmentSignUp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpFacebookLoginButton();
        ActionBar actionBar = ((FragmentActivity) getActivity()).getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        binding.btnRegister.setOnClickListener(v -> {
            Validate vName = new Validate(binding.etName);
            vName.addValidator(new NotEmptyValidator(getActivity()));

            Validate vEmail = new Validate(binding.etEmail);
            vEmail.addValidator(new EmailValidator(getActivity()));

            Validate vPhone = new Validate(binding.etPhone);
            vPhone.addValidator(new PhoneValidator(getActivity()));

            Validate vPassword = new Validate(binding.etPassword);
            vPassword.addValidator(new NotEmptyValidator(getActivity()));

            Form form = new Form();
            form.addValidates(vName, vEmail, vPhone, vPassword);

            if (form.isValid()) {
                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String phone = binding.etPhone.getText().toString();
                String password = binding.etPassword.getText().toString();
                initializeInjector(name, email, phone, password);
                signUpPresenter.signUp();
            }
        });
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
        binding.btnLogin.setReadPermissions("email");
        binding.btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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