package com.permutassep.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.lalongooo.permutassep.R;
import com.permutassep.ui.ActivityWelcome;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivityWelcome {

    @Rule
    public ActivityTestRule<ActivityWelcome> mActivityRule = new ActivityTestRule<>(ActivityWelcome.class);
    private String mWelcomeActivityTitle;
    private String mWelcomeActivityAcceptButtonText;
    private String mWelcomeActivityDeclineButtonText;

    @Before
    public void initValidString() {
        mWelcomeActivityTitle = "¡Gracias por usar Permutas SEP!";
        mWelcomeActivityAcceptButtonText = "Aceptar";
        mWelcomeActivityDeclineButtonText = "Cancelar";
    }

    @Test
    public void validateWelcomeActivityTitleTextContent() {
        onView(withId(R.id.tvWelcomeTitle))
                .check(matches(withText(mWelcomeActivityTitle)));
    }

    @Test
    public void validateWelcomeActivityAcceptTextViewContent() {
        onView(withId(R.id.button_accept))
                .check(matches(withText(mWelcomeActivityAcceptButtonText)));
    }

    @Test
    public void validateWelcomeActivityDeclineTextViewContent() {
        onView(withId(R.id.button_decline))
                .check(matches(withText(mWelcomeActivityDeclineButtonText)));
    }

    @Test
    public void validateAcceptButtonRedirectsToViewPagerIntroActivity() {
        onView(withId(R.id.button_accept))
                .perform(click());
        onView(withId(R.id.start_messaging_button))
                .check(matches(isDisplayed()));
    }
}