package com.permutassep.presentation.view.activity;

import android.os.Bundle;

import com.lalongooo.permutassep.R;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.ActivityComponent;
import com.permutassep.presentation.internal.di.components.DaggerActivityComponent;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.navigation.Navigator;
import com.permutassep.presentation.view.fragment.FragmentNewPassword;

public class ActivityNewPassword extends BaseActivity
        implements HasComponent<ActivityComponent>, Navigator.NavigationListener {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_activity_new_password);
        initializeInjector();
        setTitle(R.string.new_password_toolbar_title);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentNewPassword()).commit();
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((AndroidApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    @Override
    public void onNextFragment(Class c) {

    }
}
