package com.example.cmput301f17t27.nume.various;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.account.Profile;

/**
 * Activity where the user can view their list of
 * notifications.
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class NotificationsActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int UPDATE_PROFILE_RESULT_CODE = 2;
    //Profile var declaration
    public static Profile profile;

    //UI declarations
    private ListView notificationList;
    public static NotificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //Un-bundle the profile sent from the activity where the drawer button was clicked
        Bundle bundle = getIntent().getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //UI definitions
        notificationList = (ListView) findViewById(R.id.notificationlist);
        adapter = new NotificationAdapter(this, profile.getRequestList());

        //Set the adapter for the list of habits
        notificationList.setAdapter(adapter);
    }


    /**
     * finishes the activity
     */
    @Override
    public void onBackPressed() {
        //Bundle up the profile and return to the habit list activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("PROFILE", profile);
        intent.putExtras(bundle);
        setResult(UPDATE_PROFILE_RESULT_CODE, intent);
        finish();
    }
}
