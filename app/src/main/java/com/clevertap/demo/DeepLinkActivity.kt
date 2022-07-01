package com.clevertap.demo

import android.os.Bundle

class DeepLinkActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)
    }

   /* override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        CleverTapAPI.getDefaultInstance(applicationContext)
            ?.pushNotificationClickedEvent(intent?.extras)
        Toast.makeText(this, "On new intent called on deep link activity", Toast.LENGTH_LONG).show()
    }*/
}