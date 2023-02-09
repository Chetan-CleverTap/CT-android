package com.clevertap.mylibrary;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

public class LibApp extends Application {

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        CleverTapAPI.getDefaultInstance(this);
    }
}
