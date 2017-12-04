package com.example.cmput301f17t27.nume;

import android.content.Context;
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
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;

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
                //Get the username value from the UI field
                String userStr = userName.getText().toString();

                //Try loading the profile with that username
                Profile profile = SaveLoadController.loadProfile(LoginActivity.this, userStr);

                //If the profile was not found
                if(profile == null) {
                    //Alert the user
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Username doesn't exist. Please try again.",
                            Toast.LENGTH_SHORT);
                    newToast.show();
                }

                //If the profile was found
                else {
                    Intent intent = new Intent(LoginActivity.this, HabitListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PROFILE", profile);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
            if (resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }
}
