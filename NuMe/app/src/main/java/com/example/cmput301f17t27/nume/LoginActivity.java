package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CREATE_ACCOUNT_REQUEST_CODE = 105;

    //UI declarations
    private EditText userName;
    private Button loginButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //deleteFile("profile.sav");
        //deleteFile("events.sav");
        //deleteFile("event.sav");

        //UI definitions
        userName = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.loginbutton);
        createButton = (Button) findViewById(R.id.createbutton);


        //Setup the login button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Start the getProfile AsyncTask
                ElasticSearchController.GetProfileTask getProfileTask = new ElasticSearchController.GetProfileTask();
                getProfileTask.execute(userName.getText().toString());

                try {
                    //Get the profile from the task
                    Profile profile = getProfileTask.get();

                    //If the profile exists
                    if(profile != null) {
                        //Bundle up the profile and start the habit list activity
                        Intent intent = new Intent(LoginActivity.this, HabitListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PROFILE", profile);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }

                    //If the profile doesn't exist
                    else {
                        //Alert the user
                        Toast newToast = Toast.makeText(getApplicationContext(),
                                "That username doesn't exist. Try again", Toast.LENGTH_SHORT);
                        newToast.show();
                    }
                }

                catch (Exception e){
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Error, please try again.", Toast.LENGTH_SHORT);
                    newToast.show();
                }
            }
        });


        //Setup the create account button listener
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT_REQUEST_CODE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the EditEventActivity
        if (requestCode == CREATE_ACCOUNT_REQUEST_CODE) {
            //Do nothing
        }
    }
}
