package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    // Result codes for returning back to the HabitList activity
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int PROFILE_CHANGED_RESULT_CODE = 4;

    private String nameString;

    private EditText newName;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        newName = (EditText) findViewById(R.id.newName);
        saveButton = (Button) findViewById(R.id.savebutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        // save the new name
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameString = newName.getText().toString();

                if(nameString.length() != 0) {

                    Intent intent = new Intent();
                    intent.putExtra("newName", nameString);
                    setResult(PROFILE_CHANGED_RESULT_CODE, intent);
                    finish();

                }
                // if nothing is entered in to the EditText
                else {
                    Toast.makeText(EditProfileActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // cancel the operation
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noChangeIntent();

            }
        });
    }

    /**
     * If nothing is changed, execute this intent.
     *
     * @author Matt
     */
    private void noChangeIntent() {

        Intent intent = new Intent();
        setResult(NO_ACTION_RESULT_CODE, intent);
        finish();
    }
}
