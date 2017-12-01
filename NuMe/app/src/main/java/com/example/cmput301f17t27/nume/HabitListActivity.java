package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*imported widget*/
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class HabitListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Request codes for startingActivityForResult
    protected static final int ADD_HABIT_REQUEST_CODE = 100;
    protected static final int VIEW_HABIT_REQUEST_CODE = 101;
    protected static final int EDIT_PROFILE_REQUEST_CODE = 102;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_ADDED_RESULT_CODE = 1;
    protected static final int HABIT_DELETED_RESULT_CODE = 2;
    protected static final int HABIT_CHANGED_RESULT_CODE = 3;
    protected static final int PROFILE_CHANGED_RESULT_CODE = 4;

    //Profile var declaration
    private Profile profile;

    //Index for which habit has been clicked in the list
    private int index;

    //Bool for finding out if you're on today's events or not
    private boolean isItToday;

    //UI declarations
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView nav;
    private View navHeader;
    private TextView fullName;
    private ActionBarDrawerToggle toggle;
    private HabitAdapter adapter;
    private ListView habitList;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sidebar);

        //make isItToday (meaning today's events) false
        isItToday = false;

        //Un-bundle the profile sent from the login screen (The one on elastic search)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //Update the profile
        updateProfile();

        //Setup the drawer and toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);

        //UI definitions
        navHeader = nav.getHeaderView(0);
        fullName = (TextView) navHeader.findViewById(R.id.fullname);
        habitList = (ListView) findViewById(R.id.habitlist);
        adapter = new HabitAdapter(this, profile.getHabitList());
        addButton = (Button) findViewById(R.id.addbutton);

        //Set the user's name in the drawer
        fullName.setText(profile.getName());

        //Set the adapter for the list of habits
        habitList.setAdapter(adapter);


        //Setup the listener for the list of habits
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Save the index of the habit that was pressed
                index = position;
                Habit habit;

                //check if we're on today's events
                if(isItToday) {
                    ArrayList<Habit> todaysList = profile.getTodaysHabitList();
                    habit = todaysList.get(index);
                    index = profile.getPosition(habit);
                }
                else {
                    habit = profile.getHabit(index);
                }
                Log.d("GGG", Integer.toString(index));

                //Bundle up the habit and start the view habit activity
                Intent intent = new Intent(HabitListActivity.this, ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                // Habit habit = profile.getHabit(position);
                bundle.putSerializable("HABIT", habit);
                bundle.putInt("INDEX", index);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_HABIT_REQUEST_CODE);
            }
        });


        //Setup the listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitListActivity.this, AddHabitActivity.class);
                startActivityForResult(intent, ADD_HABIT_REQUEST_CODE);
            }
        });
    }



    //Functions for the habit_list_toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logout) {
            deleteFile("profile.sav");
            deleteFile("events.sav");
            deleteFile("event.sav");
            Intent intent = new Intent(HabitListActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    //Functions for the drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habitsbutton) {
            isItToday = false;
            adapter= new HabitAdapter(this, profile.getHabitList());
            habitList.setAdapter(adapter);
            drawer.closeDrawer(GravityCompat.START);
        }

        else if (id == R.id.eventsearchbutton) {

            Intent intent = new Intent(HabitListActivity.this, searchEventActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Profile",profile);
            intent.putExtras(bundle);

            startActivity(intent);


        }else if(id == R.id.todayshabitsbutton){
            //HabitAdapter UpdatingHabitAdapter = new HabitAdapter(this, profile.getTodaysHabitList());
            isItToday = true;
            adapter= new HabitAdapter(this, profile.getTodaysHabitList());
            habitList.setAdapter(adapter);
            //habitList.setAdapter(UpdatingHabitAdapter);
            drawer.closeDrawer(GravityCompat.START);

        }else if (id == R.id.mapbutton) {
            Intent intent = new Intent(HabitListActivity.this, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Profile",profile);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        else if (id == R.id.followersbutton) {

        }

        else if (id == R.id.followeesbutton) {

        }

        else if (id == R.id.editprofilebutton) {
            Intent intent = new Intent(HabitListActivity.this, EditProfileActivity.class);
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're returning from the AddHabit activity
        if(requestCode == ADD_HABIT_REQUEST_CODE) {
            //If the user clicked the add button
            if(resultCode == HABIT_ADDED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.addHabit(habit);
                adapter.notifyDataSetChanged();
                SaveLoadController.saveProfileToFile(HabitListActivity.this, profile);
            }

            //If the user clicked the cancel button
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the ViewHabit activity
        else if(requestCode == VIEW_HABIT_REQUEST_CODE){
            //If the user changed the habit in any way
            if(resultCode == HABIT_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.setHabit(index, habit);
                adapter.notifyDataSetChanged();
                SaveLoadController.saveProfileToFile(HabitListActivity.this, profile);
                if(isItToday) {
                    adapter= new HabitAdapter(this, profile.getTodaysHabitList());
                    habitList.setAdapter(adapter);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }

            //If the user deleted the habit
            else if(resultCode == HABIT_DELETED_RESULT_CODE) {
                profile.deleteHabit(index);
                adapter.notifyDataSetChanged();
                SaveLoadController.saveProfileToFile(HabitListActivity.this, profile);
                if(isItToday) {
                    adapter= new HabitAdapter(this, profile.getTodaysHabitList());
                    habitList.setAdapter(adapter);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }

            //If the user didn't change the habit in any way
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        // If you're returning from the EditProfileActivity
        else if(requestCode == EDIT_PROFILE_REQUEST_CODE) {
            // if the user changed their full name
            if(resultCode == PROFILE_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                String newName = bundle.getString("newName");
                profile.setName(newName);
                adapter.notifyDataSetChanged();
                fullName.setText(newName);
                SaveLoadController.saveProfileToFile(HabitListActivity.this, profile);
            }

            // if the user didn't change their profile in any way
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }



    /**
     * We assume that the data stored locally will always be newer, or just
     * as new as what's on elastic search. For this reason we will always use
     * the profile stored locally, unless it is null. In this case, we will use
     * the profile from elastic search and also save that profile locally to
     * keep things consistent. (The only time the local profile will be null is
     * if we are signing in for the first time).
     */
    private void updateProfile() {
        //If the local profile exists
        Profile localProfile = SaveLoadController.loadProfileFromFile(this);
        if(localProfile != null) {

            EventCache eventsCache = SaveLoadController.loadEventsFromFile(this);
            if(eventsCache != null) {

                ArrayList<HabitEvent> habitEvents = eventsCache.getEvents();
                int eventsIndex = eventsCache.getIndex();

                EventCache eventCache = SaveLoadController.loadEventFromFile(this);
                if(eventCache != null) {
                    HabitEvent habitEvent = eventCache.getEvent();
                    int eventIndex = eventCache.getIndex();

                    habitEvents.set(eventIndex, habitEvent);
                }

                Habit habit = localProfile.getHabit(eventsIndex);
                habit.setEvents(habitEvents);
                localProfile.setHabit(eventsIndex, habit);
            }

            profile = localProfile;
        }
        //If it doesn't, use the one on elastic search (It's already been assigned in onCreate)
        else {
            //Save the profile that's on elastic search locally
            SaveLoadController.saveProfileToFile(this, profile);
        }
    }
}
