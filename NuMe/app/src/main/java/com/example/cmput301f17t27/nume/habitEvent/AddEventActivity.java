package com.example.cmput301f17t27.nume.habitEvent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.various.Permissions;
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
public class AddEventActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CHOOSE_IMAGE_REQUEST_CODE = 106;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_ADDED_RESULT_CODE = 3;

    //Image used var declaration
    private boolean imageUsed;

    //UI declarations
    private ImageView image;
    private EditText comment;
    private Switch location;
    private Button addButton;
    private Button cancelButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Image used var definition
        imageUsed = false;

        //UI definitions
        image = (ImageView) findViewById(R.id.image);
        comment = (EditText) findViewById(R.id.comment_add);
        location = (Switch) findViewById(R.id.location);
        addButton = (Button) findViewById(R.id.addbutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Set image to default
        image.setImageResource(R.drawable.ic_menu_camera);


        //Setup the image button listener
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(AddEventActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                //If the permission has already been granted
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    //Start the image choosing activity
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, CHOOSE_IMAGE_REQUEST_CODE);
                }

                //If not, then ask for it
                else {
                    Permissions.requestReadPermission(AddEventActivity.this);
                }
            }
        });


        //Setup the location switch listener
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                location.setChecked(isChecked);
                //If the location switch is checked
                if (isChecked) {
                    //Get the current permission
                    int permission = ActivityCompat.checkSelfPermission(AddEventActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    //If the permission hasn't been granted
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        Permissions.requestLocationPermission(AddEventActivity.this);
                    }

                    //Get the permission status again
                    permission = ActivityCompat.checkSelfPermission(AddEventActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    //If it still hasn't been granted
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        location.setChecked(false);
                    }
                }
            }
        });


        //Setup the add button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the location if the switch is checked
                if (location.isChecked()) {
                    FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(AddEventActivity.this);
                    try {
                        client.getLastLocation().addOnSuccessListener(AddEventActivity.this, new OnSuccessListener<Location>() {
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
            public void onClick(View v) {
                //Return back to the ViewHabit activity
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
        //Return back to the ViewHabit activity
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
        byte[] imageArr;
        if(imageUsed) {
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap imageBmp = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageArr = stream.toByteArray();
        }
        else {
            imageArr = null;
        }

        //Get the comment from the UI field
        String commentStr = comment.getText().toString();

        //Create a habit event object with the UI values
        HabitEvent habitEvent = new HabitEvent(imageArr, commentStr, locationD);

        //Bundle up the habit event object and return to the view habit activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EVENT", habitEvent);
        intent.putExtras(bundle);
        setResult(EVENT_ADDED_RESULT_CODE, intent);
        finish();
    }
}
