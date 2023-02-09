package com.clevertap.demo

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.data.apply {
            try {
                if (size > 0) {
                    val extras = Bundle()
                    for ((key, value) in this) {
                        extras.putString(key, value)
                    }
                    val info = CleverTapAPI.getNotificationInfo(extras)
                    if (info.fromCleverTap) {
                        if (extras.containsKey("sticky")) {
                           //TODO: Create your custom sticky notification here-
                           // set the ongoing flag to true for the NotificationBuilder by-
                           // calling notificationBuilder.setOngoing(true);
                        } else {
                            CleverTapAPI.createNotification(applicationContext, extras)
                        }
                    } else {
                        // not from CleverTap handle yourself or pass to another provider
                    }
                }
            } catch (t: Throwable) {
                Log.d("MYFCMLIST", "Error parsing FCM message", t)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MyApp.getCleverTapDefaultInstance()?.pushFcmRegistrationId(token, true)
    }
}