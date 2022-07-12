package com.example.iou.activities;

import static com.example.iou.IOUKeys.NOTIFICATION_CHANNEL_KEY;
import static com.example.iou.IOUKeys.NOTIFICATION_CHANNEL_NAME_KEY;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.iou.R;

import java.util.Calendar;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager notificationManager;
    private Context context;

    // Constructor to be used when creating a notification
    public NotificationUtils(Context context) {
        super(context);
        this.context = context;

        // Creates a notification channel to send push notification
        createNotificationChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body) {

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_KEY)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_KEY, NOTIFICATION_CHANNEL_NAME_KEY, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            // Register the channel with notification manager
            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public NotificationManager getManager()
    {
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public void setNotificationTime()
    {
        // TESTING NOTIFICATIONS
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 4);
        calendar.set(calendar.SECOND, 0);

        Intent intent = new Intent(context, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

}
