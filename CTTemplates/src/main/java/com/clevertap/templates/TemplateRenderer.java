package com.clevertap.templates;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clevertap.templates.nd.CustomButton;
import com.clevertap.templates.nd.NativeDisplayListener;
import com.clevertap.templates.nd.VideoFragment;
import com.clevertap.templates.nd.story.StoryAdapter;
import com.clevertap.templates.pn.PushNotificationListener;
import com.clevertap.templates.pn.PushTemplateRenderer;

import org.json.JSONException;
import org.json.JSONObject;

public class TemplateRenderer {

    private static TemplateRenderer instance;

    public static TemplateRenderer getInstance() {
        if (instance == null) {
            return new TemplateRenderer();
        } else {
            return instance;
        }
    }

    public void showNativeDisplay(@IdRes int buttonPushProfile, FragmentManager supportFragmentManager,
                                  JSONObject jsonObject, NativeDisplayListener listener) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(buttonPushProfile, VideoFragment.newInstance(jsonObject, listener));
        transaction.commit();
    }

    public void animateButton(Context applicationContext, ViewGroup viewGroup, JSONObject jsonObject,
                              NativeDisplayListener listener) {
        new CustomButton(applicationContext, jsonObject, viewGroup, listener);
    }

    public void showPushNotification(Context applicationContext, Bundle extras, PushNotificationListener listener) {
        PushTemplateRenderer.getInstance().render(applicationContext, extras, listener);
    }

    public StoryAdapter displayStories(Activity context, JSONObject jsonObject,
                                         Boolean shouldAppend) {
        try {
            return new StoryAdapter(context, jsonObject.getJSONArray("content"), (NativeDisplayListener) context, shouldAppend);
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
