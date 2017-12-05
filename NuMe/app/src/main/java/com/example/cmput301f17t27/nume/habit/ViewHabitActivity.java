package com.example.cmput301f17t27.nume.habit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;
import com.example.cmput301f17t27.nume.habitEvent.ViewEventActivity;
import com.example.cmput301f17t27.nume.habitEvent.AddEventActivity;
import com.example.cmput301f17t27.nume.habitEvent.EventAdapter;
import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

/**
 * Activity where the user view one of their habits
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class ViewHabitActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int ADD_EVENT_REQUEST_CODE = 102;
    protected static final int VIEW_EVENT_REQUEST_CODE = 103;
    protected static final int EDIT_HABIT_REQUEST_CODE = 104;
    protected static final int VIEW_STATS_REQUEST_CODE = 105;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int UPDATE_PROFILE_RESULT_CODE = 2;
    protected static final int EVENT_ADDED_RESULT_CODE = 3;
    protected static final int HABIT_EDITED_RESULT_CODE = 4;

    //Profile declaration
    private Profile profile;

    //Index of this habit in the HabitListActivity
    private int habitIndex;

    //UI declarations
    private Toolbar toolbar;
    private TextView title;
    private TextView reason;
    private TextView date;
    private TextView frequency;
    private EventAdapter adapter;
    private ListView eventList;
    private Button editButton;
    private Button deleteButton;
    private Button addEventButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        //Un-bundle the habit and index and save to the member vars
        Bundle bundle = getIntent().getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");
        habitIndex = bundle.getInt("HABITINDEX");

        //UI definitions
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        reason = (TextView) findViewById(R.id.reason);
        date = (TextView) findViewById(R.id.date);
        frequency = (TextView) findViewById(R.id.frequency);
        eventList = (ListView) findViewById(R.id.eventlist);
        editButton = (Button) findViewById(R.id.editbutton);
        deleteButton = (Button) findViewById(R.id.deletebutton);
        addEventButton = (Button) findViewById(R.id.addeventbutton);

        //Setup the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Put the habit values in the the UI fields
        fillInUI();

        //Setup the event list adapter
        adapter = new EventAdapter(this, profile.getHabit(habitIndex).getEvents());
        eventList.setAdapter(adapter);


        //Setup the event list listener
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Bundle up the habit event and start the view habit event activity
                Intent intent = new Intent(ViewHabitActivity.this, ViewEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                bundle.putSerializable("HABITINDEX", habitIndex);
                bundle.putSerializable("EVENTINDEX", i);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_EVENT_REQUEST_CODE);
            }
        });


        //Setup the listener for the add event button
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Start the add event activity
                Intent intent = new Intent(ViewHabitActivity.this, AddEventActivity.class);
                startActivityForResult(intent, ADD_EVENT_REQUEST_CODE);
            }
        });


        //Setup the listener for the edit habit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle up the habit object and start the edit habit activity
                Intent intent = new Intent(ViewHabitActivity.this, EditHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HABIT", profile.getHabit(habitIndex));
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_HABIT_REQUEST_CODE);
            }
        });


        //Setup the listener for the delete habit button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete the habit from the profile
                profile.deleteHabit(habitIndex);

                //Save the profile
                SaveLoadController.saveProfile(ViewHabitActivity.this, profile);

                //Return back to the habit list activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                intent.putExtras(bundle);
                setResult(UPDATE_PROFILE_RESULT_CODE, intent);
                finish();
            }
        });
    }


    /**
     * cancels the activity
     */
    @Override
    public void onBackPressed() {
        //Bundle up the profile and return to the HabitListActivity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("PROFILE", profile);
        intent.putExtras(bundle);
        setResult(UPDATE_PROFILE_RESULT_CODE, intent);
        finish();
    }


    /**
     * Functions for the habit_list_toolbar
     * Inflate the menu. This adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_menu, menu);
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

        //If the home button was pressed
        if (id == android.R.id.home) {
            //Bundle up the profile and return to the HabitListActivity
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("PROFILE", profile);
            intent.putExtras(bundle);
            setResult(UPDATE_PROFILE_RESULT_CODE, intent);
            finish();

            return true;
        }

        //If the stats button was pressed
        else if (id == R.id.statsbutton) {
            //Bundle up the habit and start the habit stats activity
            Intent intent = new Intent(ViewHabitActivity.this, HabitStatsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("HABIT", profile.getHabit(habitIndex));
            intent.putExtras(bundle);
            startActivityForResult(intent, VIEW_STATS_REQUEST_CODE );
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the AddEventActivity
        if(requestCode == ADD_EVENT_REQUEST_CODE) {
            //If the add button was clicked in that activity
            if (resultCode == EVENT_ADDED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the event
                HabitEvent habitEvent = (HabitEvent) bundle.getSerializable("EVENT");

                //Add the event to the profile
                profile.getHabit(habitIndex).addEvent(habitEvent);
                adapter.notifyDataSetChanged();

                //Save the profile
                SaveLoadController.saveProfile(ViewHabitActivity.this, profile);

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the cancel button was clicked in that activity
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the ViewEventActivity
        else if(requestCode == VIEW_EVENT_REQUEST_CODE) {
            //If the profile needs to be updated
            if (resultCode == UPDATE_PROFILE_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                profile = (Profile) bundle.getSerializable("PROFILE");
                adapter = new EventAdapter(this, profile.getHabit(habitIndex).getEvents());
                eventList.setAdapter(adapter);
            }
        }

        //If you're returning from the EditHabitActivity
        else if(requestCode == EDIT_HABIT_REQUEST_CODE) {
            //If the save button was clicked in the activity
            if(resultCode == HABIT_EDITED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the habit and save it to the profile
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.setHabit(habitIndex, habit);

                //Save the profile
                SaveLoadController.saveProfile(ViewHabitActivity.this, profile);

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the cancel button was clicked in the activity
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the HabitStatsActivity
        else if(requestCode == VIEW_STATS_REQUEST_CODE ) {
            //If the back button was clicked in the activity
            if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }


    /**
     *
     */
    private void fillInUI() {
        title.setText(profile.getHabit(habitIndex).getTitle());
        reason.setText(profile.getHabit(habitIndex).getReason());
        date.setText(profile.getHabit(habitIndex).getDateToStart().toString());
        StringBuilder sb = new StringBuilder();
        for( String s : profile.getHabit(habitIndex).getFrequency()){
            sb.append(s);
            sb.append("\t");
        }
        frequency.setText(sb.toString());
    }
}
