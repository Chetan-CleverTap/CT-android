package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.mylibrary.MyLibraryClass
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*

@SuppressLint("StaticFieldLeak")
var clevertapDefaultInstance: CleverTapAPI? = null


class MyApp : Application()/*, CTPushNotificationListener*/ {

    companion object {
        fun getCleverTapDefaultInstance(): CleverTapAPI? {
            return clevertapDefaultInstance
        }
    }

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)

        super.onCreate()
        registerCallback()

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            this
        )
        getCleverTapDefaultInstance()?.pushEvent("App Launched")
        getCleverTapDefaultInstance()?.pushEvent("App Launched")
        getCleverTapDefaultInstance()?.pushEvent("App Launched")
        getCleverTapDefaultInstance()?.pushEvent("App Launched")
        getCleverTapDefaultInstance()?.pushEvent("AppLaunched")

        MyLibraryClass.getInstance(this);

        CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler());

        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty(
            "ct_objectId",
            Objects.requireNonNull(CleverTapAPI.getDefaultInstance(this))?.cleverTapID
        )

//        clevertapDefaultInstance?.ctPushNotificationListener = this;

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


    private fun registerCallback() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                CleverTapAPI.setAppForeground(true)
                Log.d("DEBUG_ANDROID_S", "OnActivityCreated " + activity.javaClass.name)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    try {
                        CleverTapAPI.getDefaultInstance(applicationContext)!!
                            .pushNotificationClickedEvent(activity.intent.extras)
                    } catch (t: Throwable) {
                    }
                }
                try {
                    val intent = activity.intent
                    val data: Uri? = intent.data
                    CleverTapAPI.getDefaultInstance(applicationContext)!!.pushDeepLink(data)
                } catch (t: Throwable) {
                }

                try {
                    //Require to close notification on action button click
                    activity.intent?.extras?.apply {
                        getString("actionId")?.let {
                            Log.d("ACTION_ID", it)
                            val autoCancel = getBoolean("autoCancel", true)
                            val notificationId = getInt("notificationId", -1)
                            if (autoCancel && notificationId > -1) {
                                val notifyMgr: NotificationManager =
                                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                notifyMgr.cancel(notificationId)
                            }
                            Toast.makeText(baseContext, "Action ID is: $it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (t: Throwable) {
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d("DEBUG_ANDROID_S", "onActivityResumed")

                try {
                    CleverTapAPI.onActivityResumed(activity)
                } catch (t: Throwable) {
                    // Ignore
                }
            }

            override fun onActivityPaused(activity: Activity) {
                try {
                    CleverTapAPI.onActivityPaused()
                } catch (t: Throwable) {
                    // Ignore
                }
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d("DEBUG_ANDROID_S", "onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

//    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
//        Log.d("DEBUG_ANDROID_S", "Clicked callback")
//    }
}