package com.example.iou.activities;

import static com.example.iou.IOUKeys.IS_FIRST_TIME_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Checks to see if a user is already logged in
        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final Button btnSignup = findViewById(R.id.btnSignup);

        // Brings the user to the login process when login button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        // Brings user to sign up for an account if the Signup button is clicked
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });
    }
    private void toLogin() {
        // Retrieves the username and password the user inputted
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        // Logs a user into Parse
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    return;
                }
                // Brings the user to the Main Activity once they have signed in
                goMainActivity();
            }
        });
    }

    // Allows a user to register for an account
    private void signupUser() {
        // Brings the user to the Signup Activity to register an account
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }

    // Returns the user to the timeline
    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra(IS_FIRST_TIME_KEY, Parcels.wrap(false));
        startActivity(i);
        finish();
    }
}