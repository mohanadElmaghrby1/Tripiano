package com.mohannad.tripiano.alarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;
import com.mohannad.tripiano.ui.trips.TripActivity;
import com.mohannad.tripiano.ui.trips.addTrip.AddTripActivity;


/**
 * Created by mohannad on 8/30/16.
 */
public class NotificationService extends IntentService {

    private static final String ACTION_SNOOZE = "Sonze";
    private static final String EXTRA_NOTIFICATION_ID = "_____ID";
    private static final String CHANNEL_ID = "101";
    private NotificationManager mManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationService(String name) {
        super(name);
    }
    public NotificationService(){
        super("tripiano");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent snoozeIntent = new Intent(this, AlarmReceiver.class);
        // Create an explicit intent for an Activity in your app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_add, ACTION_SNOOZE,
                        snoozePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(10, builder.build());

//        // Getting Notification Service
//        mManager = (NotificationManager) this.getApplicationContext()
//                .getSystemService(
//                        this.getApplicationContext().NOTIFICATION_SERVICE);
//        if (intent != null) {
//            Bundle b = intent.getExtras();
//            String TaskTitle = b.getString(AddTripActivity.COLUMN_SUBJECT);
//            long id = b.getLong(AddTripActivity._ID);
//
////            new TaskDBOpenHelper(getApplicationContext()).delete(id+"");
//
//
//            /*
//             * When the user taps the notification we have to show the Home Screen
//             * of our App, this job can be done with the help of the following
//             * Intent.
//             */
//            Intent intent1 = new Intent(this.getApplicationContext(), TripActivity.class);
//            intent1.putExtra(AddTripActivity._ID  , id);
//
//            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//
//
//            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
//                    this.getApplicationContext(),
//                    (int)id,
//                    intent1,
//                    PendingIntent.FLAG_ONE_SHOT);
//            Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
//            builder.setAutoCancel(true);
//            builder.setContentTitle("Remind");
//            builder.setContentText(TaskTitle);
//            builder.setSmallIcon(R.drawable.ic_alarm);
//            builder.setContentIntent(pendingNotificationIntent);
//            builder.setOngoing(true);
//            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//
////            builder.setSubText("Prority: " + TaskPrority);   //API level 16
//
//            if (Build.VERSION.SDK_INT < 16) {
//                mManager.notify((int)id, builder.getNotification());
//            } else {
//                mManager.notify((int)id, builder.build());
//            }
//            Notification myNotication = builder.getNotification();
//            myNotication.sound = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.hangouts_incoming_call);//Here is FILE_NAME is the name of file that you want to play
//
//            mManager.notify((int)id, myNotication);
//            Log.d("alarm" , "service starts");
//        }
    }
}
