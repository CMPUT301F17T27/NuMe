package com.example.cmput301f17t27.nume;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class AddEventActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CHOOSE_IMAGE_REQUEST_CODE = 104;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_ADDED_RESULT_CODE = 1;

    //Context declaration
    private Context context;

    //Image path declaration
    private String imagePath;

    //UI declarations
    private ImageView image;
    private EditText comment;
    private Switch location;
    private Button addButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Context definition
        context = this;

        //Image path definition
        imagePath = "";

        //UI definitions
        image = (ImageView) findViewById(R.id.image);
        comment = (EditText) findViewById(R.id.comment);
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
                if(isChecked) {
                    int permission = ActivityCompat.checkSelfPermission(AddEventActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    //If the permission has already been granted
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        Permissions.requestLocationPermission(AddEventActivity.this);
                    }
                }
            }
        });


        //Setup the add button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Get the values of the UI elements and create a habit event object
                String commentStr = comment.getText().toString();

                Location loc;
                if(location.isChecked()) {
                    Log.d("Location", "onClick: Is Location Checked");
                    int permission = ActivityCompat.checkSelfPermission(AddEventActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION);

                    //If the permission has already been granted
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        //MapController mapController = new MapController();
                        loc = MapController.getLocation(context);
                        Log.d("Location", "If granted: "+loc);
                    }
                    else {
                        loc = null;
                    }
                }
                else {
                    loc = null;
                }
                HabitEvent habitEvent = new HabitEvent(commentStr, imagePath, loc);

                //Bundle up the habit event object and return to the view habit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("EVENT", habitEvent);
                intent.putExtras(bundle);
                setResult(EVENT_ADDED_RESULT_CODE, intent);
                finish();
            }
        });


        //Setup the cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Return back to the ViewHabit activity
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
