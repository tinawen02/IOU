package com.example.iou.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder builder = notificationUtils.setNotification("IOU", "We missed you!");
        notificationUtils.getManager().notify(100, builder.build());
    }
}