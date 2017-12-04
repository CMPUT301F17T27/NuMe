package com.example.cmput301f17t27.nume.follows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;
/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class ProfileSearchActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;

    //Profile declaration
    private Profile profile;

    //UI declarations
    EditText userName;
    Button addButton;
    Button backButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);

        //Get the profile from the following activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //UI definitions
        userName = (EditText) findViewById(R.id.username);
        addButton = (Button) findViewById(R.id.followbutton);
        backButton = (Button) findViewById(R.id.backbutton);


        //Setup the listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the username from the UI field
                String userStr = userName.getText().toString();

                //Try getting a profile with that username
                Profile profile2 = SaveLoadController.getProfile(userStr);

                //If the profile exists
                if(profile2 != null) {
                    profile2.addRequest(profile.getUserName());
                    SaveLoadController.updateProfile(profile2);
                    finish();
                }

                //If the profile does not exist
                else {
                    //Alert the user
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Username doesn't exist. Please try again.",
                            Toast.LENGTH_SHORT);
                    newToast.show();
                }
            }
        });


        //Setup the listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Return to the following activity
                Intent intent = new Intent();
                setResult(NO_ACTION_RESULT_CODE, intent);
                finish();
            }
        });
    }
}
