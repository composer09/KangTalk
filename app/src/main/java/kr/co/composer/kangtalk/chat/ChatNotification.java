package kr.co.composer.kangtalk.chat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.activity.ChatActivity;

/**
 * Created by composer10 on 2015. 9. 22..
 */
public class ChatNotification {
    private static final int NOTIFY = 01;
    private Context context;
    private PendingIntent pendingIntent;
    private NotificationCompat.Builder notification;
    private NotificationManager notiManager;

    public ChatNotification(Context context) {
        this.context = context;
        init();

    }

    private void init() {
        notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(context, 0
                , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification = (NotificationCompat.Builder) new NotificationCompat.Builder(context.getApplicationContext())
                .setTicker("KangTalk!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(false);
    }

    public void setNoti(String title,String text) {
        notification.setContentTitle(title).setContentText(text);
        notiManager.notify(NOTIFY, notification.build());
    }

    public void cancel() {
        notiManager.cancel(NOTIFY);
    }

}
