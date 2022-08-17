package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.clevertap.android.sdk.CleverTapAPI

open class BaseActivity : AppCompatActivity() {

    @SuppressLint("ServiceCast")
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Toast.makeText(this, "On new intent called on deep link activity", Toast.LENGTH_LONG).show()

        Log.d("DEBUG_ANDROID_S", "onNewIntent called on " + this.javaClass.name)

        //Require to raise the Notification Clicked event and to trigger the onNotificationClickedPayloadReceived() callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            CleverTapAPI.getDefaultInstance(applicationContext)
                ?.pushNotificationClickedEvent(intent?.extras)
        }

        //Require to close notification on action button click
        intent?.extras?.apply {
            getString("actionId")?.let {
                Log.d("ACTION_ID", it)
                val autoCancel = getBoolean("autoCancel", true)
                val notificationId = getInt("notificationId", -1)
                if (autoCancel && notificationId > -1) {
                    val notifyMgr: NotificationManager =
                        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notifyMgr.cancel(notificationId)
                }
                Toast.makeText(baseContext, "Action ID is: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}