package com.example.iou.home.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.activities.LoginActivity;
import com.example.iou.tutorial.activities.TutorialActivity;
import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button btnLogout = findViewById(R.id.btnLogout);
        final Button btnTutorial = findViewById(R.id.btnTutorial);
        final TextView tvAppDescription = findViewById(R.id.tvAppDescription);

        // Set the description of the app
        tvAppDescription.setText("Check out the tutorial for help using IOU");

        // Logs out the user using a button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Allows user to view tutorial
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTutorial();
            }
        });
    }

    // Logs out the user and brings the user back to the Login Activity
    private void logoutUser() {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void viewTutorial() {
        ParseUser.logOut();
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
        finish();
    }
}