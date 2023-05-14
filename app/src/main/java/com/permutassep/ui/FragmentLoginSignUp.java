package com.permutassep.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalongooo.permutassep.databinding.CaFragmentLoginSignupBinding;

public class FragmentLoginSignUp extends BaseFragment {

    private CaFragmentLoginSignupBinding binding;

    public static FragmentLoginSignUp newInstance() {
        return new FragmentLoginSignUp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentLoginSignupBinding.inflate(inflater, container, false);

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setupOnClickListeners();
        return binding.getRoot();
    }

    private void setupOnClickListeners() {
        binding.btnRegister.setOnClickListener(v -> navigationListener.onNextFragment(FragmentSignUp.class));
        binding.btnLogin.setOnClickListener(v -> navigationListener.onNextFragment(FragmentLogin.class));
        binding.tvContinue.setOnClickListener(v -> navigationListener.onNextFragment(FragmentPagedNewsFeed.class));
    }
}