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
import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Activity where the user can edit a habit that
 * they've already created
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class EditHabitActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_EDITED_RESULT_CODE = 4;

    //Habit declaration
    private Habit habit;
    private ArrayList<String> frequencyList;

    //UI declarations
    private EditText title;
    private EditText reason;
    private EditText date;
    private CheckBox sun;
    private CheckBox mon;
    private CheckBox tue;
    private CheckBox wed;
    private CheckBox thu;
    private CheckBox fri;
    private CheckBox sat;
    private Button saveButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        //Un-bundle the habit and save it to the member var
        Bundle bundle = getIntent().getExtras();
        habit = (Habit) bundle.getSerializable("HABIT");

        //Frequency list definition
        frequencyList = habit.getFrequency();

        //UI definitions
        title = (EditText) findViewById(R.id.title_edit);
        reason = (EditText) findViewById(R.id.reason_edit);
        date = (EditText) findViewById(R.id.date_edit);
        sun = (CheckBox) findViewById(R.id.sunday);
        mon = (CheckBox) findViewById(R.id.monday);
        tue = (CheckBox) findViewById(R.id.tuesday);
        wed = (CheckBox) findViewById(R.id.wednesday);
        thu = (CheckBox) findViewById(R.id.thursday);
        fri = (CheckBox) findViewById(R.id.friday);
        sat = (CheckBox) findViewById(R.id.saturday);
        saveButton = (Button) findViewById(R.id.savebutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Put the title and reason values in the the UI fields
        title.setText(habit.getTitle());
        reason.setText(habit.getReason());

        //Put the date value in its UI field
        String startDateStr = habit.getDateToStart().toString();
        String[] startDateArr = startDateStr.split(" ");
        DateFormat format = new SimpleDateFormat("MMM", Locale.ENGLISH);
        Date month = new Date();
        try {
            month = format.parse(startDateArr[1]);
        }
        catch(java.text.ParseException e) {
            //Won't happen
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        int monthInt = cal.get(Calendar.MONTH) + 1;
        String monthStr = new String();
        if(monthInt < 10) {
            monthStr = "0" + Integer.toString(monthInt);
        }
        else {
            monthStr = Integer.toString(monthInt);
        }
        String startDate = startDateArr[2] + "/" + monthStr + "/" + startDateArr[5];
        date.setText(startDate);

        //Put the frequency values in the check boxes
        if(frequencyList.contains("Sunday")) {
            sun.setChecked(true);
        }
        if(frequencyList.contains("Monday")) {
            mon.setChecked(true);
        }
        if(frequencyList.contains("Tuesday")) {
            tue.setChecked(true);
        }
        if(frequencyList.contains("Wednesday")) {
            wed.setChecked(true);
        }
        if(frequencyList.contains("Thursday")) {
            thu.setChecked(true);
        }
        if(frequencyList.contains("Friday")) {
            fri.setChecked(true);
        }
        if(frequencyList.contains("Saturday")) {
            sat.setChecked(true);
        }


        //Setup the listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //Format the date and save to the habit object
                    String startDateStr = date.getText().toString();
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
                    habit.setDateToStart(startDate);

                    //Make a frequency list based on the check boxes and add it to the habit object
                    frequencyList = new ArrayList<>();
                    if (sun.isChecked()) {
                        frequencyList.add("Sunday");
                    }
                    if (mon.isChecked()) {
                        frequencyList.add("Monday");
                    }
                    if (tue.isChecked()) {
                        frequencyList.add("Tuesday");
                    }
                    if (wed.isChecked()) {
                        frequencyList.add("Wednesday");
                    }
                    if (thu.isChecked()) {
                        frequencyList.add("Thursday");
                    }
                    if (fri.isChecked()) {
                        frequencyList.add("Friday");
                    }
                    if (sat.isChecked()) {
                        frequencyList.add("Saturday");
                    }


                    if(frequencyList.size() == 0){
                        Toast.makeText(EditHabitActivity.this, "No dates have been selected for frequency", Toast.LENGTH_SHORT).show();
                    } else if( title.length() ==0) {
                        Toast.makeText(EditHabitActivity.this, "Please enter a Habit Title", Toast.LENGTH_SHORT).show();
                    } else if(reason.length() ==0) {
                        Toast.makeText(EditHabitActivity.this, "Please enter a Habit Reason", Toast.LENGTH_SHORT).show();
                    } else {
                        habit.setFrequency(frequencyList);

                        //Get the title and reason from the UI fields and save them to the habit object
                        habit.setTitle(title.getText().toString());
                        habit.setReason(reason.getText().toString());

                        //Bundle up the habit object and return back to the view habit activity
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("HABIT", habit);
                        intent.putExtras(bundle);
                        setResult(HABIT_EDITED_RESULT_CODE, intent);
                        finish();
                    }
                } catch (Exception e){
                    Toast.makeText(EditHabitActivity.this, "Please use DD/MM/YYYY format for Start Date", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Setup the listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return back to the view habit activity
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
        //Return back to the view habit activity
        Intent intent = new Intent();
        setResult(NO_ACTION_RESULT_CODE, intent);
        finish();
    }
}
