package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.Map;

public class EditEventActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CHOOSE_IMAGE_REQUEST_CODE = 104;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_CHANGED_RESULT_CODE = 5;

    //Context declaration
    private Context context;

    //Habit event declarations
    private HabitEvent habitEvent;
    private String imagePath;

    //UI elements
    private EditText comment;
    private ImageView image;
    private Switch location;
    private Button saveButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //Context definition
        context = this;

        //Un-bundle the habit event object and save it to the member var
        Bundle bundle = getIntent().getExtras();
        habitEvent = (HabitEvent) bundle.getSerializable("EVENT");

        //UI definitions
        comment = (EditText) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);
        location = (Switch) findViewById(R.id.location);
        saveButton = (Button) findViewById(R.id.savebutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Put the values of the habit event object into the UI fields
        String imageStr = habitEvent.getImage();
        if(imageStr == "") {
            image.setImageResource(R.drawable.ic_menu_camera);
        }
        else {
            image.setImageBitmap(BitmapFactory.decodeFile(imageStr));
        }
        comment.setText(habitEvent.getComment());
        if(habitEvent.getLocation() == null) {
            location.setChecked(false);
        }
        else {
            location.setChecked(true);
        }


        //Setup the image button listener
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start the image choosing activity
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOOSE_IMAGE_REQUEST_CODE);
            }
        });


        //Setup the save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Get the values of the UI fields and save them to the habit event object
                habitEvent.setComment(comment.getText().toString());
                habitEvent.setImage(imagePath);
                if(location.isChecked()) {
                    Log.d("Location", "onClick: Is Location Checked");
                    MapController mapController = new MapController();
                    habitEvent.setLocation(mapController.getLocation(context));
                }
                else {
                    habitEvent.setLocation(null);
                }

                //Bundle up the habit event and return back to the view event activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("EVENT", habitEvent);
                intent.putExtras(bundle);
                setResult(EVENT_CHANGED_RESULT_CODE, intent);
                finish();
            }
        });


        //Setup the cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Return back to the view event activity
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from choosing an image from the gallery
        if (requestCode == CHOOSE_IMAGE_REQUEST_CODE) {

            //If the result from choosing an image was okay
            if(resultCode == RESULT_OK && null != data) {
                //Get the path of the selected image
                Uri selectedImage = data.getData();
                String[] filePathColumn = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                        null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String selectedPath = cursor.getString(columnIndex);
                cursor.close();

                //Save the image path to the member variable
                imagePath = selectedPath;

                //Update the image being displayed
                image.setImageBitmap(BitmapFactory.decodeFile(selectedPath));
            }
        }
    }
}
