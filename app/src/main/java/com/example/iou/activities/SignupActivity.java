package com.example.iou.activities;

import static com.example.iou.IOUKeys.IS_FIRST_TIME_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.example.iou.models.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.parceler.Parcels;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsernameSignup;
    private EditText etPasswordSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsernameSignup = findViewById(R.id.etUsernameSignup);
        etPasswordSignup = findViewById(R.id.etPasswordSignup);
        final Button btnSignupSignup = findViewById(R.id.btnSignupSignup);

        // Sign up a user using the username and password
        signupUser(btnSignupSignup);
    }

    private void signupUser(Button btnSignupSignup) {
        btnSignupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Create the ParseUser
        User user = new User();

        // Set core properties
        final String username = etUsernameSignup.getText().toString();
        final String password = etPasswordSignup.getText().toString();

        // Set the views
        user.setUsername(username);
        user.setPassword(password);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    loginUser(username, password);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Error with Signup. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser (String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Error with login. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Brings the user to the Main Activity once they have signed in
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(IS_FIRST_TIME_KEY, Parcels.wrap(true));
        startActivity(i);
        finish();
    }
}