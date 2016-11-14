package com.permutassep.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.lalongooo.permutassep.R;
import com.permutassep.ui.ActivityAppOverview;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivityAppOverview {

    @Rule
    public ActivityTestRule<ActivityAppOverview> mActivityRule = new ActivityTestRule<>(ActivityAppOverview.class);

    @Before
    public void initValidString() {
    }

    @Test
    public void validateStartMessagingButtonIsDisplayed() {
        onView(withId(R.id.start_messaging_button))
                .check(matches(isDisplayed()));
    }
}