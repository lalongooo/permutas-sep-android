package com.permutassep.ui;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.lalongooo.permutassep.BuildConfig;
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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentCompleteFbData extends BaseFragment implements SignUpView {

    /**
     * UI elements
     */

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhone)
    EditText etPhone;

    @Inject
    SignUpPresenter signUpPresenter;

    private MaterialDialog progressDialog;
    private LoginCompleteListener loginCompleteListener;
    private ProgressDialog pDlg;
    private AuthenticationComponent authenticationComponent;

    /**
     * A static method to create a new instance of the {@link FragmentCompleteFbData} class
     *
     * @param bundle The incomplete data after the Facebook login
     * @return An instance of {@link FragmentPostDetail}
     */
    public static FragmentCompleteFbData newInstance(Bundle bundle) {
        FragmentCompleteFbData fragmentCompleteFbData = new FragmentCompleteFbData();
        fragmentCompleteFbData.setArguments(bundle);
        return fragmentCompleteFbData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_complete_fb_data, container, false);
        ButterKnife.bind(this, fragmentView);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginCompleteListener = (LoginCompleteListener) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initializeInjector();
    }

    private void initializeInjector() {
        String name = getArguments().getString("name");
        String email = getArguments().getString("email");
        String socialUserId = getArguments().getString("socialUserId");
        String phone = etPhone.getText().toString();
        String password = BuildConfig.com_permutassep_fb_login_dummy_password;

        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setFocusable(false);
        }

        if (!TextUtils.isEmpty(email)) {
            etEmail.setText(email);
            etEmail.setFocusable(false);
        }

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setSocialUserId(socialUserId);
        userModel.setPhone(phone);
        userModel.setPassword(password);

        this.authenticationComponent = DaggerAuthenticationComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .authenticationModule(new AuthenticationModule(userModel))
                .build();
        authenticationComponent.inject(this);
        signUpPresenter.setView(this);
    }

    @OnClick(R.id.btnContinueRegistration)
    void onBtnContinueRegistrationClick() {
        /**
         * Add validations of the EditText's
         */
        Validate vName = new Validate(etName);
        vName.addValidator(new NotEmptyValidator(getActivity()));

        Validate vEmail = new Validate(etEmail);
        vEmail.addValidator(new EmailValidator(getActivity()));

        Validate vPhone = new Validate(etPhone);
        vPhone.addValidator(new PhoneValidator(getActivity()));

        Form form = new Form();
        form.addValidates(vName, vEmail, vPhone);

        if (form.isValid()) {
            initializeInjector();
            signUpPresenter.signUp();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Methods from the {@link SignUpView} interface
     */

    @Override
    public void signedUpUser(UserModel userModel) {
        this.loginCompleteListener.onLoginComplete(userModel);
        getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        this.navigationListener.onNextFragment(FragmentPagedNewsFeed.class);
    }

    @Override
    public void showDuplicateEmailErrorMessage() {
        this.showToastMessage(getString(R.string.app_sign_up_compl_fb_data_duplicated_email));
    }

    @Override
    public void showLoading() {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.wizard_post_dlg_title)
                .content(R.string.wizard_post_dlg_text)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();

    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }
}
