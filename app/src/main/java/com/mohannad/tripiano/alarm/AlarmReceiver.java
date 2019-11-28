package com.mohannad.tripiano.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.data.model.Trip;
import com.mohannad.tripiano.ui.trips.TripActivity;
import com.mohannad.tripiano.ui.trips.addTrip.AddTripActivity;
import com.mohannad.tripiano.utils.TimePickerFragment;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Created by deepmetha on 8/30/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String START="start";
    public static final String CANCEL="cancel";

//    private final String TAG = getClass().getName();
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if(intent != null) {
//            Bundle b = intent.getExtras();
//            String TaskTitle = b.getString(AddTripActivity.COLUMN_SUBJECT);
//            long _id = b.getLong(AddTripActivity._ID);
//            Intent myIntent = new Intent(context, NotificationService.class);
//            myIntent.putExtra(AddTripActivity.COLUMN_SUBJECT, TaskTitle);
//            myIntent.putExtra(AddTripActivity._ID,_id);
//            context.startService(myIntent);
//            //alarm reciver
//            Log.d(TAG , "alarm receiver");
//        }
//
//    }
//}
private static final String CHANNEL_ID = "1";
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        if (action!=null && action.equals(START)){
            openMapActivity(context);
            Trip trip = (Trip) intent.getSerializableExtra("trip");
            updateTrip(trip);
            return;
        }else if(action!=null && action.equals(CANCEL)){
            //cancel
            return;
        }

        createNotificationChannel(context);
        Intent tripsintent =new Intent(context, AlarmReceiver.class);
        Trip trip = (Trip) intent.getSerializableExtra("trip");
        if (trip==null)
            trip=new Trip();

        tripsintent.putExtra("trip",trip);

        tripsintent.putExtra("action" , START);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, tripsintent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,tripsintent,PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent tripsintentCancel =new Intent(context, AlarmReceiver.class);
////        tripsintentCancel.putExtra("action" , CANCEL);
//        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context,1,tripsintentCancel,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .addAction(0,"Start",pendingIntent)
//                .addAction(0,"Snooze",CustomPendingIntentSnooze)
//                .addAction(0,"Cancel",pendingIntentCancel)
                .setTicker( " it is the time  to to take ")
                .setContentTitle(trip==null?"test":trip.getName())
                .setContentIntent(pendingIntent)
                .setContentText("From : " + "location ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +context.getPackageName()+"/"+R.raw.hangouts_incoming_call))
                .setVibrate(new long[]{0, 500, 1000});

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(10,builder.build());
    }
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "1", importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void openMapActivity(Context context){
//         Creates an Intent that will load a map of San Francisco
        Uri gmmIntentUri = Uri.parse("geo:"+"46.53"+","+53);
        Log.d("alarm" , "open map");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);

    }

    private void updateTrip(Trip trip){

        Backendless.Data.save(trip, new AsyncCallback<Trip>() {
            @Override
            public void handleResponse(Trip response) {
                Log.d("alarm" , "updated "+response.getStatus());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("alarm error" , fault.getMessage());
            }
        });
    }
}