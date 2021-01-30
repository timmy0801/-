package com.example.juuuuuuuuu.Notigications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.juuuuuuuuu.Chatroom;
import com.example.juuuuuuuuu.ShareCost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URI;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented = remoteMessage.getData().get("sented");
        String user = remoteMessage.getData().get("user");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null && sented.equals(firebaseUser.getUid())){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendOreoNotification(remoteMessage);

            } else {
                sendNotification(remoteMessage);

            }

        }
    }



    private void sendOreoNotification(RemoteMessage remoteMessage){
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String fuck=user.replaceAll("[\\D]", "");
        int j=0;
        Long a = Long.valueOf(fuck);
        if (a>Integer.MAX_VALUE){
            String fc=String.valueOf(a/Integer.MAX_VALUE);
            j=Integer.valueOf(fc);
        }else {
            String fc=String.valueOf(a);
            j=Integer.valueOf(fc);
        }


        if (!title.equals("您有新的記帳通知")){
            Intent intent = new Intent(this, Chatroom.class);
            Bundle bundle = new Bundle();
            bundle.putString("fid", user);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            OreoNotification oreoNotification = new OreoNotification(this);
            Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                    defaultSound, icon);
            int i = 0;
            if (j > 0){
                i = j;
            }

            oreoNotification.getManager().notify(i, builder.build());
        }else {
            Intent intent = new Intent(this, ShareCost.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            OreoNotification oreoNotification = new OreoNotification(this);
            Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                    defaultSound, icon);
            int i = 0;
            if (j > 0){
                i = j;
            }

            oreoNotification.getManager().notify(i, builder.build());
        }




    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        Log.d("w1",""+user);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String fuck=user.replaceAll("[\\D]", "");
        int j=0;
        Long a = Long.valueOf(fuck);
        if (a>Integer.MAX_VALUE){
            String fc=String.valueOf(a/Integer.MAX_VALUE);
            j=Integer.valueOf(fc);
        }else {
            String fc=String.valueOf(a);
            j=Integer.valueOf(fc);
        }
        Log.d("w",""+j);


        if (!title.equals("您有新的記帳通知")){
            Intent intent = new Intent(this, Chatroom.class);
            Bundle bundle = new Bundle();
            bundle.putString("fid", user);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(Integer.parseInt(icon))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker("fuck")
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setContentIntent(pendingIntent);
            NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            int i = 0;
            if (j > 0){
                i = j;
            }

            noti.notify(i, builder.build());
        }
        else {
            Intent intent = new Intent(this, ShareCost.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(Integer.parseInt(icon))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker("fuck")
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setContentIntent(pendingIntent);
            NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            int i = 0;
            if (j > 0){
                i = j;
            }

            noti.notify(i, builder.build());
        }

    }
}
