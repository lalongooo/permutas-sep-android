package com.permutassep.presentation.view.wizard.model;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.permutassep.presentation.view.wizard.ui.PostTextFragment;

import java.util.ArrayList;

public class PostTextPage extends Page {

    public static final String TEXT_DATA_KEY = "text";

    public PostTextPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return PostTextFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Información adicional", mData.getString(TEXT_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(TEXT_DATA_KEY));
    }

}
