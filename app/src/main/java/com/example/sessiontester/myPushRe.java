package com.example.sessiontester;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.parse.ParsePushBroadcastReceiver;


public class myPushRe extends ParsePushBroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected NotificationChannel getNotificationChannel(Context context, Intent intent) {
//        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.cake);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



            NotificationChannel mChannel = new NotificationChannel("new_id",
                    "Sound Alerts",
                    NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            // Configure the notification channel.
            mChannel.setDescription("channel description");
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(sound, attributes); // This is IMPORTANT

            return mChannel;
    }
}
