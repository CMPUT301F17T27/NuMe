package com.example.cmput301f17t27.nume.habit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
* AddHabitActivity class created by Dylan Waters on Oct 27, 2017
* Creates a Habit based on the User inputs
* Currently passes variables back and saves them into a file
 * @author Dylan & Xuan
 * @version 0.1
*  @see HabitListActivity
 * @since 0.1
* */
public class AddHabitActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_ADDED_RESULT_CODE = 1;

    //Frequency list declaration
    private ArrayList<String> frequencyList;

    //UI declarations
    private EditText title;
    private EditText reason;
    private EditText date;
    private CheckBox sun;
    private CheckBox mon;
    private CheckBox tues;
    private CheckBox wed;
    private CheckBox thurs;
    private CheckBox fri;
    private CheckBox sat;
    private Button addButton;
    private Button cancelButton;

    /**
     * Sets up the UI to create a habit
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        //Frequency list definition
        frequencyList = new ArrayList<String>();

        //UI definitions
        title = (EditText) findViewById(R.id.title);
        reason = (EditText) findViewById(R.id.reason);
        date = (EditText) findViewById(R.id.date);
        sun = (CheckBox) findViewById(R.id.sunday);
        mon = (CheckBox) findViewById(R.id.monday);
        tues = (CheckBox) findViewById(R.id.tuesday);
        wed = (CheckBox) findViewById(R.id.wednesday);
        thurs = (CheckBox) findViewById(R.id.thursday);
        fri = (CheckBox) findViewById(R.id.friday);
        sat = (CheckBox) findViewById(R.id.saturday);
        addButton = (Button) findViewById(R.id.addbutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Setup the listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get input from text fields
                String titleStr = title.getText().toString();
                String reasonStr = reason.getText().toString();
                String startDateStr = date.getText().toString();

                try {
                    //Format the date from the text field
                    String[] startDateArr = startDateStr.split("/");
                    int day = Integer.parseInt(startDateArr[0]);
                    int month = Integer.parseInt(startDateArr[1]);
                    int year = Integer.parseInt(startDateArr[2]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month - 1);
                    calendar.set(Calendar.DATE, day);
                    Date startDate = calendar.getTime();

                    //Add checked dates to frequencyList
                    if (sun.isChecked()) {
                        frequencyList.add("Sunday");
                    }
                    if (mon.isChecked()) {
                        frequencyList.add("Monday");
                    }
                    if (tues.isChecked()) {
                        frequencyList.add("Tuesday");
                    }
                    if (wed.isChecked()) {
                        frequencyList.add("Wednesday");
                    }
                    if (thurs.isChecked()) {
                        frequencyList.add("Thursday");
                    }
                    if (fri.isChecked()) {
                        frequencyList.add("Friday");
                    }
                    if (sat.isChecked()) {
                        frequencyList.add("Saturday");
                    }


                    //Check inputs and prompt for changes or return to HabitListActivity
                    if (frequencyList.size() == 0) {
                        Toast.makeText(AddHabitActivity.this, "No dates have been selected for frequency", Toast.LENGTH_SHORT).show();
                    }

                    else if( titleStr.length() == 0) {
                        Toast.makeText(AddHabitActivity.this, "Please enter a Habit Title", Toast.LENGTH_SHORT).show();
                    }

                    else if(reasonStr.length() == 0) {
                        Toast.makeText(AddHabitActivity.this, "Please enter a Habit Reason", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        //Create a new habit object with the UI entries
                        Habit habit = new Habit(titleStr, reasonStr, startDate, frequencyList);

                        //Bundle up the habit object and return to habit list activity
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("HABIT", habit);
                        intent.putExtras(bundle);
                        setResult(HABIT_ADDED_RESULT_CODE, intent);
                        finish();
                    }
                }

                catch (Exception e){
                    Toast.makeText(AddHabitActivity.this, "Please use DD/MM/YYYY format for Start Date", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Setup the listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return to HabitListActivity
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }
        });
    }


    /**
     * Cancels the activity
     */
    @Override
    public void onBackPressed() {
        //Return to HabitListActivity
        Intent intent = new Intent();
        setResult(NO_ACTION_RESULT_CODE, intent);
        finish();
    }
}
