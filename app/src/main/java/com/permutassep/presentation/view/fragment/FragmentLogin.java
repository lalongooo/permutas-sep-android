package com.permutassep.presentation.view.fragment;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.internal.di.components.ApplicationComponent;
import com.permutassep.presentation.internal.di.components.AuthenticationComponent;
import com.permutassep.presentation.internal.di.components.DaggerAuthenticationComponent;
import com.permutassep.presentation.internal.di.modules.AuthenticationModule;
import com.permutassep.presentation.model.UserModel;
import com.permutassep.presentation.presenter.LoginPresenter;
import com.permutassep.presentation.view.LoginView;
import com.permutassep.presentation.view.activity.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentLogin extends BaseFragment implements LoginView {

    /**
     * UI elements
     */

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Inject
    LoginPresenter loginPresenter;
    private AuthenticationComponent authenticationComponent;

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
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.btnLogin)
    void onBtnLoginClick() {

        String email = etName.getText().toString();
        String password = etPassword.getText().toString();

        this.authenticationComponent = DaggerAuthenticationComponent.builder()
                .applicationComponent(getComponent(ApplicationComponent.class))
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .authenticationModule(new AuthenticationModule(email, password))
                .build();
        authenticationComponent.inject(this);
        loginPresenter.setView(this);
        loginPresenter.login();
    }

    /**
     * Methods from the {@link LoginView} interface
     */

    @Override
    public void authorizeUser(UserModel userModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
