package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class ViewHabitActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int ADD_EVENT_REQUEST_CODE = 100;
    protected static final int VIEW_EVENT_REQUEST_CODE = 101;
    protected static final int EDIT_HABIT_REQUEST_CODE = 102;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_DELETED_RESULT_CODE = 2;
    protected static final int HABIT_CHANGED_RESULT_CODE = 3;
    protected static final int EVENT_ADDED_RESULT_CODE = 1;
    protected static final int EVENT_DELETED_RESULT_CODE = 4;
    protected static final int EVENT_CHANGED_RESULT_CODE = 5;

    //Habit declarations
    private Habit habit;
    private boolean changed;

    //Index of this habit in the HabitListActivity (For saving locally)
    private int habitIndex;

    //Index for which habit event has been clicked in the list
    private int eventIndex;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        //Un-bundle the habit and index and save to the member vars
        Bundle bundle = getIntent().getExtras();
        habit = (Habit) bundle.getSerializable("HABIT");
        habitIndex = bundle.getInt("INDEX");

        //Define the changed var
        changed = false;

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
        adapter = new EventAdapter(this, habit.getEvents());
        eventList.setAdapter(adapter);


        //Setup the event list listener
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Save the index of the habit that was pressed
                eventIndex = i;

                //Bundle up the habit event and start the view habit event activity
                Intent intent = new Intent(ViewHabitActivity.this, ViewEventActivity.class);
                Bundle bundle = new Bundle();
                HabitEvent habitEvent = habit.getEvent(i);
                bundle.putSerializable("EVENT", habitEvent);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_EVENT_REQUEST_CODE);
            }
        });


        //Setup the listener for the add event button
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Start the add event activity

                Intent intent = new Intent(ViewHabitActivity.this, AddEventActivity.class);
                //just pass the habitName
                Bundle passHabitbundle = new Bundle();
                passHabitbundle.putSerializable("habitName",habit);
                intent.putExtras(passHabitbundle);
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
                bundle.putSerializable("HABIT", habit);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_HABIT_REQUEST_CODE);
            }
        });


        //Setup the listener for the delete habit button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return back to the habit list activity
                Intent intent = new Intent();
                setResult(HABIT_DELETED_RESULT_CODE, intent);
                finish();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            if(changed) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("HABIT", habit);
                intent.putExtras(bundle);
                setResult(HABIT_CHANGED_RESULT_CODE, intent);
                finish();
            }

            else {
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



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

                //Add the event to the habit
                habit.addEvent(habitEvent);
                adapter.notifyDataSetChanged();

                //Save the habit events locally
                SaveLoadController.saveEventsToFile(ViewHabitActivity.this, habit.getEvents(),
                        habitIndex);

                //Set changed to true because we made changes to the habit
                changed = true;

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
            //If the save button was clicked in that activity
            if (resultCode == EVENT_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the the habit event object
                HabitEvent habitEvent = (HabitEvent) bundle.getSerializable("EVENT");

                //Change the event in the event list
                habit.setEvent(eventIndex, habitEvent);
                adapter.notifyDataSetChanged();

                //Save the habit events locally
                SaveLoadController.saveEventsToFile(ViewHabitActivity.this, habit.getEvents(),
                        habitIndex);

                //Set changed to true because we made changes to the habit
                changed = true;

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the delete button was clicked in that activity
            else if (resultCode == EVENT_DELETED_RESULT_CODE) {
                //Delete that event from the Habit object
                habit.deleteEvent(eventIndex);
                adapter.notifyDataSetChanged();

                //Save the habit events locally
                SaveLoadController.saveEventsToFile(ViewHabitActivity.this, habit.getEvents(),
                        habitIndex);

                //Set changed to true because we made changes to the habit
                changed = true;

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the cancel button was clicked in that activity
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the EditHabitActivity
        else if(requestCode == EDIT_HABIT_REQUEST_CODE) {

            //If the save button was clicked in the activity
            if(resultCode == HABIT_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the habit and save it to the habit member var
                habit = (Habit) bundle.getSerializable("HABIT");

                //Save the habit events locally
                SaveLoadController.saveEventsToFile(ViewHabitActivity.this, habit.getEvents(),
                        habitIndex);

                //Set changed to true because we made changes to the habit
                changed = true;

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the cancel button was clicked in that activity
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }



    private void fillInUI() {
        title.setText(habit.getTitle());
        reason.setText(habit.getReason());
        date.setText(habit.getDateToStart().toString());
        StringBuilder sb = new StringBuilder();
        for( String s : habit.getFrequency()){
            sb.append(s);
            sb.append("\t");
        }
        frequency.setText(sb.toString());
    }
}
