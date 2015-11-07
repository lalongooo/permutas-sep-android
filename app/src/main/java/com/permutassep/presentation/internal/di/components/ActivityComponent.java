package com.permutassep.presentation.internal.di.components;

import android.app.Activity;

import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.internal.di.modules.ActivityModule;
import com.permutassep.presentation.view.activity.ActivityMain;
import com.permutassep.presentation.view.activity.ActivityWritePost;

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

    void inject(ActivityWritePost activityWritePost);

    //Exposed to sub-graphs.
    Activity activity();
}