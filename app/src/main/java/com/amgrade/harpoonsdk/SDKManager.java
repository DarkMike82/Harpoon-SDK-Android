package com.amgrade.harpoonsdk;

import android.text.TextUtils;

/**
 * Created by michael on 27.05.15.
 */
public class SDKManager {
    //api settings
    private static String sApiVersion = "v1";

    //app data
    private static String sAppId;
    private static String sAppSecret;
    private static String sAppBundle; //app package name

    //user data
    private static int sUserId;
    private static String sUserToken;

    public static void init(String appId, String appSecret, String appBundle) {
        sAppId = appId;
        sAppSecret = appSecret;
        sAppBundle = appBundle;
    }

    public void setApiVersion(String version) {
        if (TextUtils.isEmpty(version)) {
            sApiVersion = "v1";
        } else {
            sApiVersion = "v" + version;
        }
    }

    public void setUser(int id, String token) {
        sUserId = id;
        sUserToken = token;
    }

    public static String getApiVersion() {
        return sApiVersion;
    }

    public static String getAppId() {
        return sAppId;
    }

    public static String getAppSecret() {
        return sAppSecret;
    }

    public static String getAppBundle() {
        return sAppBundle;
    }

    public static int getUserId() {
        return sUserId;
    }

    public static String getUserToken() {
        return sUserToken;
    }

}
