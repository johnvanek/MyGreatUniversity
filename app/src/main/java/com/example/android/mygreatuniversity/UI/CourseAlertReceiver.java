package com.example.android.mygreatuniversity.UI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.mygreatuniversity.R;

public class CourseAlertReceiver extends BroadcastReceiver {
    //Declarations
    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Pass in the context for getString to work and Channel Id so it know what channel
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        //This is from the android docs but we can ignore since at a minimum we are using OREO
        // Which is Android 8 or API greater than or equal to 26.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// Do the things}

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