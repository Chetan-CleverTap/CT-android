package com.clevertap.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.clevertap.android.sdk.CleverTapAPI

open class BaseActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "On new intent called on deep link activity", Toast.LENGTH_LONG).show()
        Log.d("DEBUG_ANDROID_S", "onNewIntent BaseActivity")
        CleverTapAPI.getDefaultInstance(applicationContext)
            ?.pushNotificationClickedEvent(intent?.extras)
    }
}