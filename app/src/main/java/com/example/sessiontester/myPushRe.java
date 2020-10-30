package com.example.sessiontester;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.parse.ParsePushBroadcastReceiver;


public class myPushRe extends ParsePushBroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected NotificationChannel getNotificationChannel(Context context, Intent intent) {
        return new NotificationChannel("my push", "General Books", NotificationManager.IMPORTANCE_HIGH);
    }
}
