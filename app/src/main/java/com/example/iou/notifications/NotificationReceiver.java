package com.example.iou.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Builds a notification based on the specified message and title
        NotificationUtils notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder builder = notificationUtils.setNotification("We missed you!", "Check out the map for our updated features!");
        notificationUtils.getManager().notify(0, builder.build());
    }
}