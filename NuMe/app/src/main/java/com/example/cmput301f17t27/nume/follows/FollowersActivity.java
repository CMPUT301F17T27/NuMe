package com.example.cmput301f17t27.nume.follows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;

import java.util.ArrayList;

/**
 * Activity where the user can view their list of followers
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class FollowersActivity extends AppCompatActivity {
    //Profile var declaration
    private Profile profile;

    //UI declarations
    private FollowerAdapter adapter;
    private ListView followerList;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        //Un-bundle the profile sent from the other root activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //UI definitions
        followerList = (ListView) findViewById(R.id.followerlist);

        //Get the profiles from ElasticSearch that have the usernames
        ArrayList<Profile> followerProfiles = new ArrayList<>();
        for(String userName : profile.getFollowerList()) {
            Profile profile2 = SaveLoadController.getProfile(userName);
            //If the user is not connected to the internet
            if(profile2 == null) {
                Toast newToast = Toast.makeText(getApplicationContext(),
                        "Please check connection",
                        Toast.LENGTH_SHORT);
                newToast.show();
                finish();
            }
            followerProfiles.add(profile2);
        }
        adapter = new FollowerAdapter(this, followerProfiles);

        //Set the adapter for the list of habits
        followerList.setAdapter(adapter);
    }
}
