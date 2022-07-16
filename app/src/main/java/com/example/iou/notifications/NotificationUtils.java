package com.example.iou.notifications;

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

import com.example.iou.MainActivity;
import com.example.iou.R;

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

        // Allows a user to click on a notification
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_KEY)
                .setContentIntent(resultPendingIntent)
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

    public void setNotificationTime(long milliSeconds)
    {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 51);
        calendar.set(calendar.SECOND, 0);

        Intent intent = new Intent(context, com.example.iou.notifications.NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

         */

        // when a user opens the app, set an alarm to trigger in 7 days
        // if they close the app, cancel the alarm and set a new alarm
        //


        Intent intent = new Intent(context, com.example.iou.notifications.NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, milliSeconds, pendingIntent);
    }

}