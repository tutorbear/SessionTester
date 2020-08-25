package com.example.sessiontester;


import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.parse.fcm.ParseFirebaseMessagingService;

public class MyMessagingService extends ParseFirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        registerToken(token);
    }

    private void registerToken(String token) {
        Log.d("CustomParse", " Updating FCM token");
        Log.d("CustomParse", " Token is :"+token);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if (installation != null && token != null) {
            installation.setDeviceToken(token);
            //even though this is FCM, calling it gcm will work on the backend
            installation.setPushType("gcm");
            installation.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("CustomParse", "FCM token saved to installation");
                    } else {
                        Log.d("CustomParse", "FCM token upload failed", e);
                    }
                }
            });
        }
    }
}
