package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI

@SuppressLint("StaticFieldLeak")
var clevertapDefaultInstance: CleverTapAPI? = null


class MyApp : Application() {

    companion object {
        fun getCleverTapDefaultInstance(): CleverTapAPI? {
            return clevertapDefaultInstance
        }
    }

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            this
        )

        CleverTapAPI.createNotificationChannelGroup(
            this,
            "YourGroupId",
            "YourGroupName"
        )
        CleverTapAPI.createNotificationChannel(
            applicationContext, "test", "test", "test",
            NotificationManager.IMPORTANCE_MAX, "YourGroupId", true
        )
    }
}