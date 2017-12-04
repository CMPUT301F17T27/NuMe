package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int UPDATE_PROFILE_RESULT_CODE = 2;

    //Profile var declaration
    private Profile profile;

    //UI declarations
    private EditText fullName;
    private Button saveButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Un-bundle the profile sent from the activity where the drawer button was clicked
        Bundle bundle = getIntent().getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //UI definitions
        fullName = (EditText) findViewById(R.id.fullname_edit);
        saveButton = (Button) findViewById(R.id.savebutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Fill in the name UI field
        fullName.setText(profile.getName());


        //Setup the listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the name string from the UI field
                String newName = fullName.getText().toString();

                //If the UI field is not empty
                if(newName.length() != 0) {
                    //Update the name in the profile
                    profile.setName(newName);

                    //Save the profile
                    SaveLoadController.saveProfile(EditProfileActivity.this, profile);

                    //Bundle up the profile and return to the habit list activity
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PROFILE", profile);
                    intent.putExtras(bundle);
                    setResult(UPDATE_PROFILE_RESULT_CODE, intent);
                    finish();
                }

                //If the UI field is empty
                else {
                    Toast.makeText(EditProfileActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Setup the listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }
        });
    }
}