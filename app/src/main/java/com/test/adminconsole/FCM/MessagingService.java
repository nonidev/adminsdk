package com.test.adminconsole.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.test.adminconsole.HomeActivity;
import com.test.adminconsole.R;
import com.test.adminconsole.application.MyApplication;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {

    public static final String PRIMARY_CHANNEL = "default";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    private Intent intent = null;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String image = null;
        if(remoteMessage.getData().containsKey("image")){
            image = remoteMessage.getData().get("image");
        }

        final Random random = new Random(1000);

        if(remoteMessage.getData().containsKey("webview")){
            intent = new Intent(this, HomeActivity.class);
            intent.putExtra("URL",remoteMessage.getData().get("webview"));
            intent.putExtra("isdirect",true);
        }else{
            intent = new Intent(this, HomeActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT); // (context, request code, intent, flags)
        notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSound(alarmSound);
        notificationBuilder.setTicker(message);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX );
        notificationBuilder.setAutoCancel(true);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL,getString(R.string.noti_channel_default), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(channel);
        }else{

        }


        ImageRequest imageRequest = new ImageRequest(image, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                notificationManager.notify(random.nextInt(), notificationBuilder.build());
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificationManager.notify(random.nextInt(), notificationBuilder.build());
            }
        });

        if(image!=null){
            MyApplication.Companion.getInstance().addToRequestQueue(imageRequest);
        }else {
            notificationManager.notify(random.nextInt(), notificationBuilder.build());
        }


    }

}