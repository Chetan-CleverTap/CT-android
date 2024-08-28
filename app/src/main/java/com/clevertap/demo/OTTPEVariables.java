package com.clevertap.demo;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class OTTPEVariables {

    HashMap<String, Object> ott = new HashMap<>();
    HashMap<String, Object> ads = new HashMap<>();
    HashMap<String, Object> home = new HashMap<>();
    HashMap<String, Object> homeScreenOrder = new HashMap<>();
    HashMap<String, Object> homeScreenViewType = new HashMap<>();
    HashMap<String, Object> player = new HashMap<>();
    HashMap<String, Object> user = new HashMap<>();

    public OTTPEVariables(CleverTapAPI cleverTapAPI) {

        homeScreenOrder.put("bigBanner", 0);
        homeScreenOrder.put("continueWatching", 1);
        homeScreenOrder.put("hot", 2);
        homeScreenOrder.put("new", 3);
        homeScreenOrder.put("videoBanner", 4);
        homeScreenOrder.put("upcoming", 5);
        homeScreenOrder.put("latestTV", 6);
        homeScreenOrder.put("categories", 7);

        homeScreenViewType.put("bigBanner", 0);
        homeScreenViewType.put("continueWatching", 1);
        homeScreenViewType.put("hot", 2);
        homeScreenViewType.put("new", 3);
        homeScreenViewType.put("videoBanner", 4);
        homeScreenViewType.put("upcoming", 5);
        homeScreenViewType.put("latestTV", 6);
        homeScreenViewType.put("categories", 6);

        home.put("featured_content", "1234");
        home.put("homeScreenOrder", homeScreenOrder);
        home.put("homeScreenViewType", homeScreenViewType);

        ads.put("adCapPerHour", 0);
        ads.put("adsCoolDownMinutes", 10);
        ads.put("adsDurationMinutes", 0.5);
        ads.put("enableAds", true);

        player.put("ads", ads);
        player.put("allowSeek", true);

        user.put("userType", "AVOD");
        user.put("plan", "Yearly");

        ott.put("player", player);
        ott.put("home", home);
        ott.put("user", user);

        cleverTapAPI.defineVariable("OTT", ott);
        cleverTapAPI.syncVariables();
    }
}