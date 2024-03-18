package com.clevertap.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler
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

                    if (extras.containsKey("nm")) {
                        // Raise the event
                    }
                    val info = CleverTapAPI.getNotificationInfo(extras)
                    if (info.fromCleverTap) {
                        if (extras.containsKey("sticky")) {
                           //TODO: Create your custom sticky notification here-
                           // set the ongoing flag to true for the NotificationBuilder by-
                           // calling notificationBuilder.setOngoing(true);
//                            sendBroadcast( Intent("MyAction"));
                            sendBroadcast(
                                Intent(
                                    applicationContext,
                                    MyReceiver::class.java
                                ).setAction("MyAction")
                            )

//                            showPIP()
                        } else {
//                            CleverTapAPI.createNotification(applicationContext, extras)
                            CleverTapAPI.processPushNotification(applicationContext,extras);

                            CTFcmMessageHandler()
                                .createNotification(applicationContext, message);

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