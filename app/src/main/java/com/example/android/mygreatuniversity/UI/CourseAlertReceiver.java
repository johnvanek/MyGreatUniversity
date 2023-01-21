package com.example.android.mygreatuniversity.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.android.mygreatuniversity.R;

public class CourseAlertReceiver extends BroadcastReceiver {
    //Broadcast receiver sends the notifications on Receive call
    //Declarations
    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        // This is essentially what happens when the notification is triggered.
        Log.d("courseChannel", "Received Course-end notification");
        //For now test it with a Toast
        Toast.makeText(context,intent.getStringExtra("key"),Toast.LENGTH_LONG).show();

        //Creates the channel for the notification
        createNotificationChannel(context,channel_id);
        //build the new notification using the builder and call build()
        Notification notification = new NotificationCompat.Builder(context,channel_id)
                .setSmallIcon(R.drawable.toolbar_icon_small)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("NotificationTest").build();

        //Pass it over to the System Service Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Call notify from the System and increment the notification ID, pass in the current notification
        notificationManager.notify(notificationID++,notification);
        //Don't need to actually throw this its just a reminder for implementation
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    //Pass in the context for getString to work and Channel Id so it know what channel
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        //Define the names and the description
        //This get the values for the channel name & Description from res.values
        CharSequence name = context.getString(R.string.channel_name_course);
        String description = context.getString(R.string.channel_description_course);

        //Define the level of importance for the channel
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        //Create a notification Channel object with a unique ID
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        //This is optional this shows what the user see's in their notification settings
        channel.setDescription(description);

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}