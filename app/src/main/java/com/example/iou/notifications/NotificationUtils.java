package com.example.iou.notifications;

import static com.example.iou.IOUKeys.FRAGMENT_KEY;
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

import androidx.core.app.NotificationCompat;

import com.example.iou.MainActivity;
import com.example.iou.R;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager notificationManager;
    private final Context context;

    public NotificationUtils(Context context) {
        super(context);
        this.context = context;
        // Creates a notification channel to send push notification
        createNotificationChannel();
    }

    public NotificationCompat.Builder setNotification(String title, String body) {
        // Sets the intent that will fire when a user clicks on the notification
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(FRAGMENT_KEY, "map");
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Allows a user to click on the bill action
        PendingIntent billPendingIntent = initializeBillIntent();

        NotificationCompat.Action billAction = new NotificationCompat.Action(R.drawable.ic_notifications_black_24dp, "Create a Bill", billPendingIntent);

        // Sets the text, notification logo, and priority of the notification
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_KEY)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .addAction(billAction)
                .setPriority(NotificationCompat.PRIORITY_MAX);
    }

    private PendingIntent initializeBillIntent() {

        // Creates an action that directs the user to the map fragment
        Intent billIntent = new Intent(this, MainActivity.class);
        billIntent.putExtra(FRAGMENT_KEY, "bill");
        billIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Creates a pending intent that waits upon the user clicking the map action

        return PendingIntent.getActivity(this, 1, billIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel() {
        // Create the notification channel and set the priority
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_KEY, NOTIFICATION_CHANNEL_NAME_KEY, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        // Register the channel with the system
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager()
    {
        if(notificationManager == null) {
            // Creates a NotificationManager to notify user of a notification in the background
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void setNotificationTime(long milliSeconds)
    {
        // Sets an intent to start the Broadcast Receiver
        Intent intent = new Intent(context, com.example.iou.notifications.NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Sets the notification to appear after a certain amount of time
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, milliSeconds, pendingIntent);
    }

}