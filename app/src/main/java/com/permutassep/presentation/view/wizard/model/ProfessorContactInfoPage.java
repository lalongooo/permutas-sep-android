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

package com.permutassep.presentation.view.wizard.model;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.permutassep.presentation.view.wizard.ui.ProfessorContactInfoFragment;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class ProfessorContactInfoPage extends Page {
    public static final String NAME_DATA_KEY = "name";
    public static final String EMAIL_DATA_KEY = "email";
    public static final String PHONE_DATA_KEY = "phone";

    public ProfessorContactInfoPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ProfessorContactInfoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Tu nombre", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Tu correo", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Tu telefono", mData.getString(PHONE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
    }
}
