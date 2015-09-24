package com.permutassep.presentation.view.activity;

import android.content.Context;
import android.content.Intent;

import com.permutassep.presentation.internal.di.components.PostComponent;

/**
 * Created by lalongooo on 13/09/15.
 */
public class PostListActivity {

    private PostComponent userComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PostListActivity.class);
    }
}