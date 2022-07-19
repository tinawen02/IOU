package com.example.iou.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.notifications.NotificationUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Allows the splash screen to appear for 1 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the app once the splash screen
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                // Closes the activity
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancels the existing alarm notification
        cancelNotification();
        // Creates a new alarm notification
        createNotification();
    }

    private void cancelNotification() {
        // Gets the pending intent with the associated requestCode to be cancelled
        Intent intent  = new Intent(this, com.example.iou.notifications.NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Cancels a notification if there exists a pendingIntent
        if(pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    // Creates a push notification
    private void createNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);

        // Determines how many seconds the notification should be sent after user login
        long currentTime = System.currentTimeMillis();

        //TODO: change this into minutes
        long numSeconds = 1000 * 10;
        long triggerReminder = currentTime + numSeconds;

        notificationUtils.setNotificationTime(triggerReminder);
    }
}