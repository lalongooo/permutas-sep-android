package com.permutassep.presentation.view.fragment;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalongooo.permutassep.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentLoginSignUp extends BaseFragment {

    /**
     * A static method to create a new instance of the {@link FragmentLoginSignUp} class
     *
     * @return An instance of {@link FragmentLoginSignUp}
     */
    public static FragmentLoginSignUp newInstance() {
        return new FragmentLoginSignUp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.ca_fragment_login_signup, container, false);
        ButterKnife.bind(this, fragmentView);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        return fragmentView;
    }

    @OnClick(R.id.btnRegister)
    void onBtnRegisterClick() {
        this.navigationListener.onNextFragment(FragmentSignUp.class);
    }

    @OnClick(R.id.btnLogin)
    void onBtnLoginClick() {
        this.navigationListener.onNextFragment(FragmentLogin.class);
    }

    @OnClick(R.id.tvContinue)
    void onTvContinueClick() {
        this.navigationListener.onNextFragment(FragmentPostList.class);
    }
}