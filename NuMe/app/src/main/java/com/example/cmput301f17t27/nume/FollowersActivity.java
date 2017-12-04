package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity {
    //Profile var declaration
    private Profile profile;

    //UI declarations
    private FollowerAdapter adapter;
    private ListView followerList;


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
