package com.example.iou.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.notifications.NotificationUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.i("MainActivity", "splash getting on create");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelNotification();
        createNotification();
        Log.i("MainActivity", "splash getting destroyed");

    }

    private void cancelNotification() {
        Intent intent  = new Intent(this, com.example.iou.notifications.NotificationReceiver.class);
        intent.setAction("SomeAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            Log.i("MainActivity", "splash getting cancelled");
        }
    }

    // Creates a push notification
    private void createNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);

        // Determines how seconds the notification should be sent after user login
        long currentTime = System.currentTimeMillis();
        long numSeconds = 1000 * 3;
        long triggerReminder = currentTime + numSeconds;

        notificationUtils.setNotificationTime(triggerReminder);
        Log.i("MainActivity", "splash getting created");
    }



    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("MainActivity", "splash getting restarted");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("MainActivity", "splash getting stopped");

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("MainActivity", "splash getting paused");
    }
}