package com.clevertap.demo

import android.app.Activity
import android.app.Application
import android.content.Intent

interface MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    fun onNewIntent(intent: Intent)

}