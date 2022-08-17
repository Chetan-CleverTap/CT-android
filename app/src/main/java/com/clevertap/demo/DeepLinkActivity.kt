package com.clevertap.demo

import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener

class DeepLinkActivity : BaseActivity(), CTPushNotificationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.getCleverTapDefaultInstance()?.ctPushNotificationListener = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)
        Log.d("DEBUG_ANDROID_S", "onCreate " + this.javaClass.name)

    }

    /* override fun onNewIntent(intent: Intent?) {
         super.onNewIntent(intent)
         CleverTapAPI.getDefaultInstance(applicationContext)
             ?.pushNotificationClickedEvent(intent?.extras)
         Toast.makeText(this, "On new intent called on deep link activity", Toast.LENGTH_LONG).show()
     }*/
    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
        Log.d("DEBUG_ANDROID_S", "Clicked callback" + this.javaClass.name)
    }

}