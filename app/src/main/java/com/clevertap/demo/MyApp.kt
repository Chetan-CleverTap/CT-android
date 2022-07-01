package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.util.Log
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener

@SuppressLint("StaticFieldLeak")
var clevertapDefaultInstance: CleverTapAPI? = null


class MyApp : Application(), CTPushNotificationListener {

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

        clevertapDefaultInstance?.ctPushNotificationListener = this;

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

/*
    private fun registerCallback() {
        registerActivityLifecycleCallbacks(object : MyActivityLifecycleCallbacks {
            override fun onNewIntent(intent: Intent?) {
                try {
                    CleverTapAPI.getDefaultInstance(applicationContext)!!
                        .pushNotificationClickedEvent(intent?.extras)
                } catch (t: Throwable) {

                }
                try {
                    val data: Uri? = intent?.data
                    CleverTapAPI.getDefaultInstance(applicationContext)!!.pushDeepLink(data)
                } catch (t: Throwable) {

                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                CleverTapAPI.setAppForeground(true)
                Log.d("DEBUG_ANDROID_S", "OnActivityCreated " + activity.javaClass.name)
                try {
                    CleverTapAPI.getDefaultInstance(applicationContext)!!
                        .pushNotificationClickedEvent(activity.intent.extras)
                } catch (t: Throwable) {

                }
                try {
                    val intent = activity.intent
                    val data: Uri? = intent.data
                    CleverTapAPI.getDefaultInstance(applicationContext)!!.pushDeepLink(data)
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
*/

    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
        Log.d("DEBUG_ANDROID_S", "Clicked callback")
    }
}