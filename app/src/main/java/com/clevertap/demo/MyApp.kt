package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.mparticle.MParticle
import com.mparticle.MParticleOptions
import com.mparticle.identity.BaseIdentityTask


//@SuppressLint("StaticFieldLeak")
//var clevertapDefaultInstance: CleverTapAPI? = null


class MyApp : Application() {

//    companion object {
//        fun getCleverTapDefaultInstance(): CleverTapAPI? {
//            return clevertapDefaultInstance
//        }
//    }

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        val options = MParticleOptions.builder(this)
            .credentials("us1-146911f2b4b7864780abfc120c202877",
                "Qq1W6eWbjwcY-R0A8cI_iNLfXG-Z4x1dy0j9oLwwfKyu2ZimIBXFYIKbpALns9YR")
            .logLevel(MParticle.LogLevel.DEBUG)
            .build()

        MParticle.start(options)


//        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
//            this
//        )

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