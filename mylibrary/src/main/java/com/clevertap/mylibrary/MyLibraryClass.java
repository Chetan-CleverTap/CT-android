package com.clevertap.mylibrary;

import android.app.Application;

import com.clevertap.android.sdk.CleverTapAPI;

public class MyLibraryClass  {

    public static MyLibraryClass getInstance(Application context) {
        CleverTapAPI.getDefaultInstance(context.getApplicationContext());

        return new MyLibraryClass();
    }

    public static void main(String[] args) {

    }
}
