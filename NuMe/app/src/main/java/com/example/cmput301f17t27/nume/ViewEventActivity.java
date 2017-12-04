package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewEventActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int EDIT_EVENT_REQUEST_CODE = 107;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int UPDATE_PROFILE_RESULT_CODE = 2;
    protected static final int EVENT_EDITED_RESULT_CODE = 5;

    //Profile declaration
    private Profile profile;

    //Index of this habit in the HabitListActivity
    private int habitIndex;

    //Index of the event that was clicked in ViewHabit
    private int eventIndex;

    //UI declarations
    private TextView comment;
    private ImageView image;
    private TextView location;
    private Button editButton;
    private Button deleteButton;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        //Un-bundle the profile and indexes
        Bundle bundle = getIntent().getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");
        habitIndex = bundle.getInt("HABITINDEX");
        eventIndex = bundle.getInt("EVENTINDEX");

        //UI definitions
        comment = (TextView) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);
        location = (TextView) findViewById(R.id.location);
        editButton = (Button) findViewById(R.id.editbutton);
        deleteButton = (Button) findViewById(R.id.deletebutton);
        backButton = (Button) findViewById(R.id.backbutton);

        //Put the habit event values in the the UI fields
        fillInUI();

        //Setup the listener for the edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the event and start the edit event activity
                Intent intent = new Intent(ViewEventActivity.this, EditEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("EVENT", profile.getHabit(habitIndex).getEvent(eventIndex));
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_EVENT_REQUEST_CODE);
            }
        });


        //Setup the listener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Delete the event from the profile
                profile.getHabit(habitIndex).deleteEvent(eventIndex);

                //Save the profile
                SaveLoadController.saveProfile(ViewEventActivity.this, profile);

                //Bundle up the profile and return back to the view habit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                intent.putExtras(bundle);
                setResult(UPDATE_PROFILE_RESULT_CODE, intent);
                finish();
            }
        });


        //Setup the listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the profile and return back to the view habit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROFILE", profile);
                intent.putExtras(bundle);
                setResult(UPDATE_PROFILE_RESULT_CODE, intent);
                finish();
            }
        });
    }



    @Override
    public void onBackPressed() {
        //Bundle up the profile and return back to the view habit activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("PROFILE", profile);
        intent.putExtras(bundle);
        setResult(UPDATE_PROFILE_RESULT_CODE, intent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the EditEventActivity
        if (requestCode == EDIT_EVENT_REQUEST_CODE) {
            //If the save button was clicked in that activity
            if (resultCode == EVENT_EDITED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the event and save it to the profile
                HabitEvent habitEvent = (HabitEvent) bundle.getSerializable("EVENT");
                profile.getHabit(habitIndex).setEvent(eventIndex, habitEvent);

                //Save the profile
                SaveLoadController.saveProfile(ViewEventActivity.this, profile);

                //Put the habit values in the the UI fields
                fillInUI();
            }

            //If the cancel button was clicked in that activity
            else if (resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }



    private void fillInUI() {
        //Put the habit event values in the the UI fields
        byte[] imageArr = profile.getHabit(habitIndex).getEvent(eventIndex).getImage();
        if(imageArr == null) {
            image.setImageResource(R.drawable.ic_menu_camera);
        }
        else {
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageArr, 0, imageArr.length));
        }

        comment.setText(profile.getHabit(habitIndex).getEvent(eventIndex).getComment());

        if(profile.getHabit(habitIndex).getEvent(eventIndex).getLocation() == null) {
            location.setText("Location not used");
        }
        else {
            double lat = profile.getHabit(habitIndex).getEvent(eventIndex).getLocation()[0];
            double lon = profile.getHabit(habitIndex).getEvent(eventIndex).getLocation()[1];
            location.setText("Latitude: " + lat + "Longitude: " + lon);
        }
    }
}
