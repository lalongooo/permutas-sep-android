/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wizardpager.wizard.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.android.wizardpager.wizard.model.ProfessorContactInfoPage;
import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.constants.Constants;
import com.permutassep.model.SocialUser;
import com.permutassep.model.User;
import com.permutassep.ui.ActivityMain;

import br.kots.mob.complex.preferences.ComplexPreferences;

public class ProfessorContactInfoFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ProfessorContactInfoPage mPage;
    private TextView mNameView;
    private TextView mEmailView;
    private TextView mPhoneView;

    public static ProfessorContactInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ProfessorContactInfoFragment fragment = new ProfessorContactInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfessorContactInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ProfessorContactInfoPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_professor_info, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mNameView = ((TextView) rootView.findViewById(R.id.your_name));
        mNameView.setText(mPage.getData().getString(ProfessorContactInfoPage.NAME_DATA_KEY));

        mEmailView = ((TextView) rootView.findViewById(R.id.your_email));
        mEmailView.setText(mPage.getData().getString(ProfessorContactInfoPage.EMAIL_DATA_KEY));

        mPhoneView = ((TextView) rootView.findViewById(R.id.your_phone));
        mPhoneView.setText(mPage.getData().getString(ProfessorContactInfoPage.PHONE_DATA_KEY));
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(((ActivityMain) activity).getSupportFragmentManager().getFragments().get(0) instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) ((ActivityMain) activity).getSupportFragmentManager().getFragments().get(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(ProfessorContactInfoPage.NAME_DATA_KEY, (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(ProfessorContactInfoPage.EMAIL_DATA_KEY, (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
        
        
        mPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(ProfessorContactInfoPage.PHONE_DATA_KEY, (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getActivity(), Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        User user = complexPreferences.getObject(Constants.PREF_USER_KEY, SocialUser.class);

        if(user != null){
            mNameView.setText(user.getName() != null ? user.getName() : "");
            mEmailView.setText(user.getEmail() != null ? user.getEmail() : "");
            mPhoneView.setText(user.getPhone() != null ? user.getPhone() : "");
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mNameView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
