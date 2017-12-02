package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Matt on 2017-12-01.
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginActivityTest() {
        super(com.example.cmput301f17t27.nume.LoginActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "matt");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }

    public void testCreateAccount() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("Create Account");
        solo.waitForText("Welcome to NuMe");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
