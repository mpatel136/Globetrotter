package com.team.beanie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = context.getString(R.string.notificationTitle);
        String message = context.getString(R.string.notificationText);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentToMainActivity = new Intent(context, MainActivity.class);
        //Ensures that the activity that is being called will be replaced if needed
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intentToMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationChannel channel = new NotificationChannel("notificationChannel", "Globetrotter", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notiManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notiManager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"notificationChannel");
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.globe);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        //Makes notification dismissible on swipe
        notificationBuilder.setAutoCancel(true);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
