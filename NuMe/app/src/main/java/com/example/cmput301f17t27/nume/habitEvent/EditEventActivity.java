package com.example.cmput301f17t27.nume.habitEvent;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class EditEventActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CHOOSE_IMAGE_REQUEST_CODE = 106;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_EDITED_RESULT_CODE = 5;

    //Habit event declarations
    private HabitEvent habitEvent;

    //ImageUsed var declaration
    private boolean imageUsed;

    //UI elements
    private EditText comment;
    private ImageView image;
    private Switch location;
    private Button saveButton;
    private Button cancelButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

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
        byte[] imageArr = habitEvent.getImage();
        if(imageArr == null) {
            imageUsed = false;
            image.setImageResource(R.drawable.ic_menu_camera);
        }
        else {
            imageUsed = true;
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageArr, 0, imageArr.length));
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
                //Get the location if the switch is checked
                if (location.isChecked()) {
                    FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(EditEventActivity.this);
                    try {
                        client.getLastLocation().addOnSuccessListener(EditEventActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                double[] loc;
                                if(location != null) {
                                    //Create a location double
                                    loc = new double[] {
                                            location.getLatitude(),
                                            location.getLongitude()
                                    };
                                }

                                else {
                                    loc = null;
                                }

                                //Finish the activity
                                result(loc);
                            }
                        });
                    }

                    catch (SecurityException e){
                        //Finish the activity
                        result(null);
                    }
                }

                else {
                    //Finish the activity
                    result(null);
                }
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


    /**
     * Finished the activity
     */
    @Override
    public void onBackPressed() {
        //Return back to the view event activity
        Intent intent = new Intent();
        setResult(NO_ACTION_RESULT_CODE, intent);
        finish();
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

        //If you're returning from choosing an image from the gallery
        if (requestCode == CHOOSE_IMAGE_REQUEST_CODE) {
            //If the result from choosing an image was okay
            if(resultCode == RESULT_OK && null != data) {
                //Set the imageUsed var
                imageUsed = true;

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

                //Get the bitmap image
                Bitmap imageBmp = BitmapFactory.decodeFile(selectedPath);

                //Convert to byte array to check size
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //If the image is too big
                if(stream.toByteArray().length > 65536) {
                    //Alert the user
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Image too large. Try another image.",
                            Toast.LENGTH_SHORT);
                    newToast.show();
                }
                //If the image is not too big
                else {
                    //Update the image being displayed
                    image.setImageBitmap(imageBmp);
                }
            }
        }
    }


    /**
     * Sets up the Event to have the changes saved
     * @param locationD
     */
    private void result(double[] locationD) {
        //Get the image from the UI field
        if(imageUsed) {
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap imageBmp = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            habitEvent.setImage(stream.toByteArray());
        }
        else {
            habitEvent.setImage(null);
        }

        //Get the comment from the UI field
        habitEvent.setComment(comment.getText().toString());

        //Get the location from the argument
        habitEvent.setLocation(locationD);

        //Bundle up the habit event object and return to the view habit activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EVENT", habitEvent);
        intent.putExtras(bundle);
        setResult(EVENT_EDITED_RESULT_CODE, intent);
        finish();
    }
}
