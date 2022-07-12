package com.example.iou.activities;

import static com.example.iou.IOUKeys.IS_FIRST_TIME_KEY;
import static com.example.iou.IOUKeys.NOTIFICATION_CHANNEL_KEY;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Calendar;

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

        // Create a notification channel
        //createNotificationChannel();

        // Brings the user to the login process when login button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                createNotification();
                
                //createNotification(100, "IOU misses you", "We haven't seen you in a while", NOTIFICATION_CHANNEL_KEY);
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

    private void createNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        long currentTime = System.currentTimeMillis();
        long tenSeconds = 1000 * 3;
        long triggerReminder = currentTime + tenSeconds;
        notificationUtils.setReminder(triggerReminder);
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_KEY, "MyChannel", importance);
            channel.setDescription("Reminders");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(int nId, String title, String body, String channelId) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(calendar.SECOND, 15);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        intent.setAction("MY_NOTIFICATION_MESSAGE");


        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),nId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle(title)
                .setContentText(body);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(nId, mBuilder.build());

        /*
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());

         */
    }

}