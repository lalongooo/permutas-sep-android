package com.permutassep.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lalongooo.permutassep.R;
import com.permutassep.BaseFragment;

public class FragmentSettings extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getActivity().invalidateOptionsMenu();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}