package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
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

    public void testAStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testBLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }

    public void testCFalseLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "not a username");
        solo.clickOnButton("Login");
        solo.waitForText("That username doesn't exist");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    public void testDCreateAccount() {
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

    public void testEAddHabit() {
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

    public void testJDeleteHabit() {
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
    public void testFAddHabitEvent() {
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

    public void testIDeleteEvent() {
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
        solo.waitForText("Back");
        solo.assertCurrentActivity("Wrong Activity", ViewEventActivity.class);
        solo.clickOnButton("Delete");
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
    }

    public void testGEditHabit() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickInList(0);
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        solo.clickOnButton("Edit");
        solo.waitForText("Save");
        solo.assertCurrentActivity("Wrong Activity", EditHabitActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.title_edit));
        solo.enterText((EditText) solo.getView(R.id.title_edit), "test edit");
        solo.clickOnButton("Save");
        solo.waitForText("Events");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
    }

    public void testHEditHabitEvent() {
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
        solo.waitForText("Back");
        solo.assertCurrentActivity("Wrong Activity", ViewEventActivity.class);
        solo.clickOnButton("Edit");
        solo.waitForText("Use Location");
        solo.assertCurrentActivity("Wrong Activity", EditEventActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.comment_edit));
        solo.enterText((EditText) solo.getView(R.id.comment_edit), "test edit");
        solo.clickOnView(solo.getView(R.id.location));
        solo.clickOnButton("Save");
        solo.waitForText("Back");
        solo.assertCurrentActivity("Wrong Activity", ViewEventActivity.class);
    }

    public void testFATodaysEvents() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnImageButton(0);
        solo.sleep(1000);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.sleep(500);
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickInList(0);
        solo.waitForText("Events:");
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
    }

    public void testFBEventSearch() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnImageButton(0);
        solo.sleep(500);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.waitForActivity("searchEventActivity");
        solo.assertCurrentActivity("Wrong Activity", searchEventActivity.class);
    }

    public void testFCMaps() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnImageButton(0);
        solo.sleep(500);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.waitForActivity("MapsActivity");
        solo.clickOnView(solo.getView(R.id.radToggle));
        solo.sleep(500);
        solo.assertCurrentActivity("Wrong Activity", MapsActivity.class);
    }

    public void testDEEditProfile() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username), "intentTest");
        solo.clickOnButton("Login");
        solo.waitForText("Habits");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        solo.clickOnImageButton(0);
        solo.sleep(500);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.waitForActivity("EditProfileActivity");
        solo.enterText((EditText) solo.getView(R.id.newName), "Intent Edited");
        solo.clickOnButton("Save");
        solo.waitForActivity("HabitListActivity");
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
