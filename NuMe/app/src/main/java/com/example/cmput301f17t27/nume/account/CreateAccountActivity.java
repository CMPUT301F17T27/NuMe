package com.example.cmput301f17t27.nume.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.various.SaveLoadController;

/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class CreateAccountActivity extends AppCompatActivity {
    //UI declarations
    private EditText userName;
    private EditText fullName;
    private Button createButton;
    private Button cancelButton;

    /**
     * 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UI definitions
        userName = (EditText) findViewById(R.id.username);
        fullName = (EditText) findViewById(R.id.fullname);
        createButton = (Button) findViewById(R.id.createbutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Setup the login button listener
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Get the values from the UI fields
                String userStr = userName.getText().toString();
                String nameStr = fullName.getText().toString();

                //Construct the profile from the field values
                Profile profile = new Profile(userStr, nameStr);

                //If the profile was added
                if(SaveLoadController.addProfile(CreateAccountActivity.this, profile)) {
                    //Return to the login activity
                    finish();
                }

                //If the profile was not added
                else {
                    //Alert the user
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Username already exists, or the connection was lost",
                            Toast.LENGTH_SHORT);
                    newToast.show();
                }
            }
        });


        //Setup the create account button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }
}
