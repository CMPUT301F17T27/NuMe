package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class FollowingActivity extends AppCompatActivity {

    //Request codes for startingActivityForResult
    protected static final int VIEW_FOLLOWING_HABIT_REQUEST_CODE = 105;
    protected static final int SEARCH_PROFILE_REQUEST_CODE = 106;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;

    //Profile var declaration
    private Profile profile;

    //FollowingHabits declaration
    private ArrayList<FollowingHabit> followingHabits;

    //UI declarations
    private FollowingAdapter adapter;
    private ListView habitList;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);


        //Un-bundle the profile sent from the activity where the drawer button was clicked
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");



        //Setup the FollowingHabit list
        followingHabits = new ArrayList<>();
        ArrayList<Profile> followingProfiles = profile.getFollowingProfiles();
        for(Profile fProfile : followingProfiles) {
            for(Habit habit : fProfile.getHabitList()) {
                HabitEvent habitEvent;
                if (habit.getEvents().size() > 0) {
                    habitEvent = habit.getEvent(0);
                }
                else {
                    habitEvent = null;
                }

                FollowingHabit fHabit = new FollowingHabit(fProfile.getUserName(), habit.getTitle(),
                        habit.getReason(), habit.getDateToStart(), habit.getFrequency(), habitEvent);
                followingHabits.add(fHabit);
            }
        }

        //UI definitions
        adapter = new FollowingAdapter(this, followingHabits);
        habitList = (ListView) findViewById(R.id.followinglist);
        searchButton = (Button) findViewById(R.id.searchbutton);

        //Set the adapter for the list of habits
        habitList.setAdapter(adapter);


        //Setup the listener for the list of habits
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Bundle up the habit and start the view following habit activity
                Intent intent = new Intent(FollowingActivity.this, ViewFollowingHabitActivity.class);
                Bundle bundle = new Bundle();
                FollowingHabit habit = followingHabits.get(position);
                bundle.putSerializable("HABIT", habit);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_FOLLOWING_HABIT_REQUEST_CODE);
            }
        });


        //Setup the listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowingActivity.this, ProfileSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                intent.putExtras(bundle);
                startActivityForResult(intent, SEARCH_PROFILE_REQUEST_CODE);
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're returning from the ViewFollowingHabit activity
        if(requestCode == VIEW_FOLLOWING_HABIT_REQUEST_CODE) {
            //If the user clicked the back button
            if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the SearchProfile activity
        else if(requestCode == SEARCH_PROFILE_REQUEST_CODE){
            //If the user clicked the add or back button
            if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }
}

