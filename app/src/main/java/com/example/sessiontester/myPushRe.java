package com.example.sessiontester;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.parse.ManifestInfo;
import com.parse.PLog;
import com.parse.ParsePushBroadcastReceiver;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class myPushRe extends ParsePushBroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected NotificationChannel getNotificationChannel(Context context, Intent intent) {
        return new NotificationChannel("parse_push", "General Alerts", NotificationManager.IMPORTANCE_HIGH);
    }
}
