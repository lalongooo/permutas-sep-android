package com.example.android.wizardpager.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.android.wizardpager.wizard.ui.PostTextFragment;

import java.util.ArrayList;

/**
 * Created by lalongooo on 08/04/15.
 */
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
        dest.add(new ReviewItem("Información adicional", mData.getString(TEXT_DATA_KEY), TEXT_DATA_KEY, -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(TEXT_DATA_KEY));
    }

}
