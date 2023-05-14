package com.permutassep.ui;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.lalongooo.permutassep.BuildConfig;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentCompleteFbDataBinding;
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

public class FragmentCompleteFbData extends BaseFragment implements SignUpView {

    private CaFragmentCompleteFbDataBinding binding;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentCompleteFbDataBinding.inflate(inflater, container, false);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginCompleteListener = (LoginCompleteListener) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeInjector();
        setupOnClickListeners();
    }

    private void initializeInjector() {
        String name = getArguments().getString("name");
        String email = getArguments().getString("email");
        String socialUserId = getArguments().getString("socialUserId");
        String phone = binding.etPhone.getText().toString();
        String password = BuildConfig.com_permutassep_fb_login_dummy_password;

        if (!TextUtils.isEmpty(name)) {
            binding.etName.setText(name);
            binding.etName.setFocusable(false);
        }

        if (!TextUtils.isEmpty(email)) {
            binding.etEmail.setText(email);
            binding.etEmail.setFocusable(false);
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

    private void setupOnClickListeners() {
        /**
         * Add validations of the EditText's
         */
        Validate vName = new Validate(binding.etName);
        vName.addValidator(new NotEmptyValidator(getActivity()));

        Validate vEmail = new Validate(binding.etEmail);
        vEmail.addValidator(new EmailValidator(getActivity()));

        Validate vPhone = new Validate(binding.etPhone);
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
        binding = null;
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
