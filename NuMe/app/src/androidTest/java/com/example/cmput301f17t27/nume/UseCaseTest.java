package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by Matt on 2017-12-01.
 */

public class UseCaseTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public UseCaseTest() {
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
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }

    public void testFalseLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "not a username");
        solo.clickOnButton("Login");
        solo.waitForText("That username doesn't exist");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    public void testCreateAccount() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("Create Account");
        solo.waitForText("Welcome to NuMe");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.enterText((EditText) solo.getView(R.id.fullname), "Intent Test");
        solo.clickOnButton("Create Account");
        /*
        solo.waitForText("Welcome to NuMe");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        */
        solo.waitForText("Username already exists");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);
        solo.clickOnButton("Cancel");
        solo.waitForText("Welcome to NuMe");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    public void testAddHabit() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnButton("Add");
        solo.waitForText("Add a Habit");
        solo.assertCurrentActivity("Wrong Activity", AddHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.title), "Test Habit");
        solo.enterText((EditText) solo.getView(R.id.reason), "Test Reason");
        solo.enterText((EditText) solo.getView(R.id.date), "02/12/2017");
        solo.clickOnCheckBox(0);
        solo.clickOnCheckBox(1);
        solo.clickOnCheckBox(2);
        solo.clickOnCheckBox(3);
        solo.clickOnCheckBox(4);
        solo.clickOnCheckBox(5);
        solo.clickOnCheckBox(6);
        solo.clickOnButton("Add");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }

    public void testDeleteHabit() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickInList(0);
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickOnButton("Delete");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }
    public void testAddHabitEvent() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickInList(0);
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickOnButton("Add Event");
        solo.waitForText("Use Location");
        solo.assertCurrentActivity("Wrong Activity", AddEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment), "testEvent");
        solo.clickOnButton("Add");
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
    }

    public void testDeleteEvent() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickInList(0);
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickInList(0);
        solo.waitForText("Comment");
        solo.assertCurrentActivity("Wrong Activity", ViewEventActivity.class);
        solo.clickOnButton("Delete");
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
