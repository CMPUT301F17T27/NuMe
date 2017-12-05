package com.example.cmput301f17t27.nume.habit;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.cmput301f17t27.nume.account.EditProfileActivity;
import com.example.cmput301f17t27.nume.account.LoginActivity;
import com.example.cmput301f17t27.nume.various.MapsActivity;
import com.example.cmput301f17t27.nume.various.NotificationsActivity;
import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;
import com.example.cmput301f17t27.nume.follows.FollowersActivity;
import com.example.cmput301f17t27.nume.follows.FollowingActivity;
import com.example.cmput301f17t27.nume.habitEvent.EventSearchActivity;

/**
 * Activity where the user can view all their habits
 * as well as the navigation drawer. (This is the main
 * activity once logged in)
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class HabitListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Request codes for startingActivityForResult
    protected static final int ADD_HABIT_REQUEST_CODE = 100;
    protected static final int VIEW_HABIT_REQUEST_CODE = 101;
    protected static final int DRAWER_ACTIVITY_REQUEST_CODE = 108;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_ADDED_RESULT_CODE = 1;
    protected static final int UPDATE_PROFILE_RESULT_CODE = 2;

    //Profile var declaration
    private Profile profile;

    //SortToday declaration
    private boolean sortToday;

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

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sidebar);

        //SortToday definition
        sortToday = false;

        //Un-bundle the profile sent from the login screen (The one on elastic search)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

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

        //Display the list of habits
        filterList();


        //Setup the listener for the list of habits
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Convert the index if needed
                int index = position;
                if (sortToday) {
                    index = profile.convertIndex(position);
                }

                //Bundle up the habit and start the view habit activity
                Intent intent = new Intent(HabitListActivity.this, ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                bundle.putInt("HABITINDEX", index);
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


    /**
     * Functions for the habit_list_toolbar
     * Inflate the menu. This adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //If the filter button was pressed
        if (id == R.id.sorttoday) {
            //Toggle the sortToday var
            sortToday = !sortToday;

            //If changed to sort today's habits
            if (sortToday) {
                item.setTitle(R.string.showAll);
            }

            //If changed to show all habits
            else {
                item.setTitle(R.string.today);
            }

            filterList();
        }

        //If the logout button was pressed
        if (id == R.id.logout) {
            //Save the profile to ElasticSearch
            SaveLoadController.saveProfile(HabitListActivity.this, profile);

            //Delete the profile saved locally
            SaveLoadController.deleteLocalProfile(HabitListActivity.this);

            //Return to the login screen
            Intent intent = new Intent(HabitListActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    //Functions for the drawer
    /**
     * Cancels the Activity
     */
    @Override
    public void onBackPressed() {
        //Close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habitsbutton) {
            //Close the drawer
            drawer.closeDrawer(GravityCompat.START);

            return true;
        }

        Intent intent;
        if (id == R.id.eventsearchbutton) {
            intent = new Intent(HabitListActivity.this, EventSearchActivity.class);
        }

        else if (id == R.id.mapbutton) {
            intent = new Intent(HabitListActivity.this, MapsActivity.class);
        }

        else if (id == R.id.notificationsbutton) {
            intent = new Intent(HabitListActivity.this, NotificationsActivity.class);
        }

        else if (id == R.id.followersbutton) {
            intent = new Intent(HabitListActivity.this, FollowersActivity.class);
        }

        else if (id == R.id.followingbutton) {
            intent = new Intent(HabitListActivity.this, FollowingActivity.class);
        }

        else {
            intent = new Intent(HabitListActivity.this, EditProfileActivity.class);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("PROFILE", profile);
        intent.putExtras(bundle);
        startActivityForResult(intent, DRAWER_ACTIVITY_REQUEST_CODE);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're returning from the AddHabit activity
        if(requestCode == ADD_HABIT_REQUEST_CODE) {
            //If the user clicked the add button
            if(resultCode == HABIT_ADDED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.addHabit(habit);
                adapter.notifyDataSetChanged();
                SaveLoadController.saveProfile(HabitListActivity.this, profile);
            }

            //If the user clicked the cancel button
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the ViewHabit activity
        else if(requestCode == VIEW_HABIT_REQUEST_CODE) {
            //If the profile needs to be updated
            if(resultCode == UPDATE_PROFILE_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                profile = (Profile) bundle.getSerializable("PROFILE");
                adapter = new HabitAdapter(this, profile.getHabitList());
                habitList.setAdapter(adapter);
            }
        }

        //If you're returning from a drawer activity
        else if(requestCode == DRAWER_ACTIVITY_REQUEST_CODE) {
            //If the profile needs to be updated
            if(resultCode == UPDATE_PROFILE_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                profile = (Profile) bundle.getSerializable("PROFILE");
                adapter = new HabitAdapter(this, profile.getHabitList());
                habitList.setAdapter(adapter);
                fullName.setText(profile.getName());
            }
        }
    }


    /**
     *
     */
    private void filterList() {
        if (sortToday) {
            adapter = new HabitAdapter(this, profile.getTodaysHabitList());
            habitList.setAdapter(adapter);
        }

        else {
            adapter = new HabitAdapter(this, profile.getHabitList());
            habitList.setAdapter(adapter);
        }
    }
}
