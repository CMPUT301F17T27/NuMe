package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewFollowingHabitActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;

    //FollowingHabit declaration
    private FollowingHabit followingHabit;

    //UI declarations
    private TextView title;
    private TextView reason;
    private TextView date;
    private TextView frequency;
    private ImageView image;
    private TextView comment;
    private TextView location;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_following_habit);

        //Un-bundle the FollowingHabit
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        followingHabit = (FollowingHabit) bundle.getSerializable("HABIT");

        //UI definitions
        title = (TextView) findViewById(R.id.title);
        reason = (TextView) findViewById(R.id.reason);
        date = (TextView) findViewById(R.id.date);
        frequency = (TextView) findViewById(R.id.frequency);
        image = (ImageView) findViewById(R.id.image);
        comment = (TextView) findViewById(R.id.comment);
        location = (TextView) findViewById(R.id.location);
        backButton = (Button) findViewById(R.id.backbutton);

        //Fill in the UI
        title.setText(followingHabit.title);
        reason.setText(followingHabit.reason);
        date.setText(followingHabit.dateToStart.toString());
        frequency.setText(followingHabit.frequency.toString());
        if(followingHabit.habitEvent != null) {
            comment.setText(followingHabit.habitEvent.getComment());
            location.setText(followingHabit.habitEvent.getLocation().toString());
        }
        else {
            comment.setText("");
            location.setText("");
        }


        //Setup the listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }
        });
    }
}
