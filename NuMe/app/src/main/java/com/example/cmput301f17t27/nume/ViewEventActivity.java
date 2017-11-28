package com.example.cmput301f17t27.nume;

import android.content.Intent;
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
    protected static final int EDIT_EVENT_REQUEST_CODE = 103;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_DELETED_RESULT_CODE = 4;
    protected static final int EVENT_CHANGED_RESULT_CODE = 5;

    //Habit event declarations
    private HabitEvent habitEvent;
    private boolean changed;

    //Index of the event that was clicked in view habit (For saving locally)
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

        //Un-bundle the habit event and index and save it to the member variables
        Bundle bundle = getIntent().getExtras();
        habitEvent = (HabitEvent) bundle.getSerializable("EVENT");
        eventIndex = bundle.getInt("INDEX");

        //Define the changed var
        changed = false;

        //UI definitions
        comment = (TextView) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);
        location = (TextView) findViewById(R.id.location);
        editButton = (Button) findViewById(R.id.editbutton);
        deleteButton = (Button) findViewById(R.id.deletebutton);
        backButton = (Button) findViewById(R.id.backbutton);

        //Put the habit values in the the UI fields
        fillInUI();


        //Setup the listener for the edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the event and start the edit event activity
                Intent intent = new Intent(ViewEventActivity.this, EditEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("EVENT", habitEvent);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_EVENT_REQUEST_CODE);
            }
        });


        //Setup the listener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Return back to the view habit activity
                Intent intent = new Intent();
                setResult(EVENT_DELETED_RESULT_CODE, intent);
                finish();
            }
        });


        //Setup the listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the habit event if needed and return back to the view habit activity
                Intent intent = new Intent();
                if(changed) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EVENT", habitEvent);
                    intent.putExtras(bundle);
                    setResult(EVENT_CHANGED_RESULT_CODE, intent);
                }

                else {
                    setResult(NO_ACTION_RESULT_CODE, intent);
                }
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the EditEventActivity
        if (requestCode == EDIT_EVENT_REQUEST_CODE) {

            //If the save button was clicked in that activity
            if (resultCode == EVENT_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the event and save it to the member variable
                habitEvent = (HabitEvent) bundle.getSerializable("EVENT");

                //Save the habit event locally
                SaveLoadController.saveEventToFile(ViewEventActivity.this, habitEvent, eventIndex);

                //Set changed to true because we made changes to the habit
                changed = true;

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
        comment.setText(habitEvent.getComment());
        String imageStr = habitEvent.getImage();
        if(imageStr == "") {
            image.setImageResource(R.drawable.ic_menu_camera);
        }
        else {
            image.setImageBitmap(BitmapFactory.decodeFile(imageStr));
        }

        if(habitEvent.voidLocation()) {
            location.setText("Location not used");
        }
        else {
            double Lat = habitEvent.getLat();
            double Long = habitEvent.getLong();
            location.setText("Latitude: "+Lat+"Longitude: "+Long);
        }
    }
}
