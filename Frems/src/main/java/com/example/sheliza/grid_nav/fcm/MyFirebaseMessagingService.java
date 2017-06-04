package com.example.sheliza.grid_nav.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.sheliza.grid_nav.Notifi.Get;
import com.example.sheliza.grid_nav.RegistrationActivity;
import com.example.sheliza.grid_nav.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ishant on 05-Dec-16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    Intent intent;
    String title;
    String msg;


    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null){
            Log.i("message", "Msg Not Rcvd");
            return;
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i("message", remoteMessage.getNotification().getBody());
            Log.i("messageRecevied","Msg Rcvd");
            handleNotification(msg,title);
        }

        if (remoteMessage.getData().size() > 0) {
            Log.i( "Data Payload: " ,remoteMessage.getData().toString());
            Log.i("size", String.valueOf(remoteMessage.getData().size()));

            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                Log.i("TRY","inTry");
                handleDataMessage(json);
            } catch (Exception e) {
                Log.i( "Exception: " , e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.i( "push json: " , json.toString());

        try {

            String message = json.getString("msg");
            String  var[]=message.split("-");
            for (int k=0;k<var.length;k++){
                if(k==0){
                    msg=var[0];

                }else
                {
                    title=var[1];
                }
            }
            Log.i("ssize",var.length+"");

            Log.i("jj",var[1]);
            Log.i("title",title);

            intent = new Intent(this, Get.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("message", msg);
            intent.putExtra("title", title);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } catch (JSONException e) {
            Log.e( "Json Exception: " , e.getMessage());
        } catch (Exception e) {
            Log.e( "Exception: " , e.getMessage());
        }
    }

    private void handleNotification(String msg,String title) {

        intent = new Intent(this, Get.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", msg);
        intent.putExtra("title", title);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}
