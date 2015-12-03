package com.permutassep.presentation.internal.di.components;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.ui.ActivityMain;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent extends ApplicationComponent {

    void inject(ActivityMain activityMain);

    //Exposed to sub-graphs.
    Activity activity();

    Toolbar toolbar();
}