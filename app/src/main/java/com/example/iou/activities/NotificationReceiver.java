package com.example.iou.activities;

import static com.parse.Parse.getApplicationContext;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.iou.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils notificationUtils = new NotificationUtils(context);


        // TESTING NOTIFICATIONS
        Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = notificationUtils.setNotification("IOU", "We haven't seen you in a while!");
        notificationUtils.getManager().notify(100, builder.build());
    }
}
