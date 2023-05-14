package com.permutassep.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentNewPasswordBinding;
import com.permutassep.presentation.config.Config;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.AuthenticationComponent;
import com.permutassep.presentation.internal.di.components.DaggerAuthenticationComponent;
import com.permutassep.presentation.model.ConfirmPasswordResetDataWrapperModel;
import com.permutassep.presentation.presenter.NewPasswordPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.LoginView;
import com.permutassep.presentation.view.NewPasswordView;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import javax.inject.Inject;

public class FragmentNewPassword extends BaseFragment implements NewPasswordView {

    public static final String EXTRA_RESET_PASSWORD = "extra_reset_password";

    private CaFragmentNewPasswordBinding binding;

    @Inject
    NewPasswordPresenter newPasswordPresenter;

    private Form form;
    private MaterialDialog progressDialog;
    private String token;
    private String password;
    private String passwordConfirm;
    private String email;

    /**
     * Empty constructor
     */
    public FragmentNewPassword() {
        super();
    }

    /**
     * A static method to create a new instance of the {@link FragmentNewPassword} class
     *
     * @return An instance of {@link FragmentNewPassword}
     */
    public static FragmentNewPassword newInstance() {
        return new FragmentNewPassword();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentNewPasswordBinding.inflate(inflater, container, false);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setUI();
        initializeInjector();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnResetPassword.setOnClickListener(v -> {
            if (form.isValid()) {
                password = binding.etPasswordOne.getText().toString();
                passwordConfirm = binding.etPasswordTwo.getText().toString();

                ConfirmPasswordResetDataWrapperModel confirmPasswordResetDataWrapperModel =
                        new ConfirmPasswordResetDataWrapperModel(token, password, passwordConfirm);
                newPasswordPresenter.confirmPasswordReset(confirmPasswordResetDataWrapperModel);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = requireActivity().getIntent().getStringExtra(Config.PWD_RESET_TOKEY_KEY);
        email = requireActivity().getIntent().getStringExtra(Config.PWD_RESET_EMAIL_KEY);
    }

    private void setUI() {
        Validate vPasswordOne = new Validate(binding.etPasswordOne);
        vPasswordOne.addValidator(new NotEmptyValidator(getActivity()));
        vPasswordOne.addValidator(
                new LengthValidator(
                        getActivity(),
                        R.string.new_password_password_length_error,
                        Config.PASSWORD_MIN_LENGTH,
                        Config.PASSWORD_MAX_LENGTH)
        );

        Validate vPasswordTwo = new Validate(binding.etPasswordTwo);
        vPasswordTwo.addValidator(new NotEmptyValidator(getActivity()));
        vPasswordTwo.addValidator(
                new LengthValidator(
                        getActivity(),
                        R.string.new_password_password_length_error,
                        Config.PASSWORD_MIN_LENGTH,
                        Config.PASSWORD_MAX_LENGTH)
        );

        form = new Form();
        form.addValidates(vPasswordOne);
        form.addValidates(vPasswordTwo);
    }

    private void initializeInjector() {
        AuthenticationComponent authenticationComponent =
                DaggerAuthenticationComponent.builder()
                        .applicationComponent(getComponent(ApplicationComponent.class))
                        .activityModule(((BaseActivity) getActivity()).getActivityModule())
                        .build();
        authenticationComponent.inject(this);
        this.newPasswordPresenter.setView(this);
    }

    /**
     * Methods from the {@link LoginView} interface
     */

    @Override
    public void passwordCorrectlyReset() {
        new MaterialDialog.Builder(requireActivity())
                .title(R.string.password_reset_dlg_title)
                .content(R.string.password_reset_dlg_password_reset_successful)
                .positiveText(R.string.accept)
                .show();
        PrefUtils.clearApplicationPreferences(requireActivity());
        LoginManager.getInstance().logOut();
        navigationListener.onNextFragment(FragmentLogin.class);
    }

    @Override
    public void showLoading() {
        progressDialog = new MaterialDialog.Builder(requireActivity())
                .title(R.string.password_reset_dlg_title)
                .content(R.string.password_reset_dlg_resetting_password)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
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
        new MaterialDialog.Builder(requireActivity())
                .title(R.string.password_reset_dlg_title)
                .content(message)
                .positiveText(R.string.accept)
                .show();
    }
}
