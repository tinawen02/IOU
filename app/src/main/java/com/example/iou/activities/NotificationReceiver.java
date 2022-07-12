package com.example.iou.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder builder = notificationUtils.setNotification("IOU", "We haven't seen you in a while!");
        notificationUtils.getManager().notify(100, builder.build());
    }

}
