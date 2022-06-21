package com.example.iou.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iou.MainActivity2;
import com.example.iou.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Checks to see if a user is already logged in
        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        // Find the views by Id
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Brings the user to the login process
        toLogin(btnLogin);

        // Brings user to sign up for an account if the Signup button is clicked
        signupUser(btnSignup);
    }

    private void toLogin(Button btnLogin) {
        // Logs in the user once a username and password are entered
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    // Logs in a user using their username and password
    private void loginUser (String username, String password) {
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
    private void signupUser(Button btnSignup) {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Brings the user to the Signup Activity to register an account
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    // Returns the user to the timeline
    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(i);
        finish();
    }
}