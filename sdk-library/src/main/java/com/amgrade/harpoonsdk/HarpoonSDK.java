package com.amgrade.harpoonsdk;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by michael on 27.05.15.
 */
public class HarpoonSDK {
    private static final String TYPE_APP = "application";
    private static final String TYPE_USER = "user";

    //app data
    private static String sAppId;
    private static String sAppSecret;
    private static String sAppBundle; //app package name
    private static String sAppToken;

    //user data
    private static String sUserId;
    private static String sUserAuthCode;
    private static String sUserToken;

    //brand data
    private static int sBrandId;

    private static Context sContext;
    private static MaterialDialog sMessageDialog;

    /**
     * Init basic SDK params
     * @param context used to show info/error messages. Please provide Application or AppContext (not Activity) to avoid NPE if Activity is closed.
     * @param appId AppId for your app on https://brand.harpoonconnect.com/
     * @param appSecret Secret for your app on https://brand.harpoonconnect.com/
     * @param appBundle bundle id for your app ("package" in AndroidManifest.xml or "applicationId" in build.gradle)
     */
    public static void init(Context context, String appId, String appSecret, String appBundle) {
        sContext = context;
        sMessageDialog = new MaterialDialog.Builder(sContext)
                .title(R.string.error)
                .positiveText(R.string.ok)
                .build();

        sAppId = appId;
        sAppSecret = appSecret;
        sAppBundle = appBundle;
    }

    /*public static void setApiVersion(String version) {
        if (TextUtils.isEmpty(version)) {
            sApiVersion = "v1";
        } else {
            sApiVersion = "v" + version;
        }
    }*/

    /*public static void setBrand(int brand_id) {
        sBrandId = brand_id;
    }*/

    /**
     * Set user credentials to use in requests
     * @param id
     * @param token
     */
    public static void setUser(String id, String token) {
        sUserId = id;
        sUserToken = token;
    }

//    public static String getApiVersion() {
//        return sApiVersion;
//    }

    public static String getAppId() {
        return sAppId;
    }

    public static String getAppSecret() {
        return sAppSecret;
    }

    public static String getAppBundle() {
        return sAppBundle;
    }

    public static String getAppToken() {
        return sAppToken;
    }

    public static void setToken(String type, String token) {
        if (type.contentEquals(TYPE_APP)) {
            sAppToken = token;
        } else if (type.contentEquals(TYPE_USER)) {
            sUserToken = token;
        }
    }

    public static String getUserId() {
        return sUserId;
    }

    public static String getUserAuthCode() {
        return sUserAuthCode;
    }

    public static String getUserToken() {
        return sUserToken;
    }


    public static Context getContext() {
        return sContext;
    }

    public static void showMessage(int titleId, int contentId) {
        if (sContext == null) {
            throw new NullPointerException("SDK context is not initialised!");
        }
        if (titleId>=0) {
            sMessageDialog.setTitle(titleId);
        }
        sMessageDialog.setContent(sContext.getString(contentId));
        sMessageDialog.show();
    }

    public static void hideMessage() {
        sMessageDialog.hide();
    }

}
