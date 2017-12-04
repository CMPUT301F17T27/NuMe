package com.example.cmput301f17t27.nume.follows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;

import java.util.ArrayList;
/**
 * Template for javaDoc
 * @author
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
            followerProfiles.add(SaveLoadController.getProfile(userName));
        }
        adapter = new FollowerAdapter(this, followerProfiles);

        //Set the adapter for the list of habits
        followerList.setAdapter(adapter);
    }
}
