package com.amgrade.harpoonsdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Michael Dontsov on 27.05.15.
 */
public class HarpoonSDK {
    private static final String TAG = HarpoonSDK.class.getSimpleName();
    private static final String TOKEN_APP = "application";
    private static final String TOKEN_USER = "user";
    private static final String DOMAIN = "harpoon";

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
//    private static int sBrandId;

    private static Context sContext;
    private static MaterialDialog sMessageDialog;
    private static SharedPreferences sPrefs;

    /**
     * Init basic SDK params
     * @param context used to display info/error messages. Please provide application context<br/>
     * ({@link Application} or {@link Activity#getApplicationContext()}), not {@link Activity} to avoid NPE if Activity is closed.
     * @param appId AppId for your app on https://brand.harpoonconnect.com/
     * @param appSecret Secret for your app on https://brand.harpoonconnect.com/
     * @param appBundle bundle id for your app ("package" in {@code AndroidManifest.xml} or "applicationId" in {@code build.gradle})
     */
    public static void init(Context context, String appId, String appSecret, String appBundle) {
        if (context==null) {
            throw new NullPointerException("SDK context can't be null!");
        }
        sContext = context;
        sMessageDialog = new MaterialDialog.Builder(sContext)
                .title(R.string.error)
                .positiveText(R.string.ok)
                .build();
        if (!HarpoonBeaconManager.isAdvertisingAvailable()) {
            Log.d(TAG, sContext.getString(R.string.old_api));
        }

        sAppId = appId;
        sAppSecret = appSecret;
        sAppBundle = appBundle;

        //get SharedPreferences and load previously stored values (if any)
        sPrefs = PreferenceManager.getDefaultSharedPreferences(sContext);
        loadPreference("app.token", sAppToken);
        loadPreference("user.id", sUserId);
        loadPreference("user.code",sUserAuthCode);
        loadPreference("user.token",sUserToken);
    }

    /*public static void setApiVersion(String version) {
        if (TextUtils.isEmpty(version)) {
            sApiVersion = "v1";
        } else {
            sApiVersion = "v" + version;
        }
    }*/

    /*public static String getApiVersion() {
        return sApiVersion;
    }*/

    /**
     * Save data of validated user (after creation or login)
     * @param id user ID
     * @param auth_code authentication code (to exchange for token)
     */
    public static void setUser(String id, String auth_code) {
        sUserId = id;
        sUserAuthCode = auth_code;
        saveToPrefs("user.id", sUserId);
        saveToPrefs("user.code", sUserAuthCode);
    }

    /**
     * Remove unneeded auth_code (e.g. when token is received)
     */
    public static void clearAuthCode() {
        sUserAuthCode = null;
    }

    /**
     * Save token (for later use in auth).
     * @param type either {@link #TOKEN_APP} or {@link #TOKEN_USER}.
     * @param token token value.
     */
    public static void setToken(String type, String token) {
        if (type.contentEquals(TOKEN_APP)) {
            sAppToken = token;
            saveToPrefs("app.token", token);
        } else if (type.contentEquals(TOKEN_USER)) {
            sUserToken = token;
            saveToPrefs("user.token", token);
        }
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

    public static String getAppToken() {
        return sAppToken;
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

    private static String formatPreferenceKey(String key) {
        return String.format("%s:%s:%s", DOMAIN, getAppBundle(), key);
    }

    private static void saveToPrefs(String key, Object value) {
        String formattedKey = formatPreferenceKey(key);
        SharedPreferences.Editor editor = sPrefs.edit();
        if (value instanceof String) {
            editor.putString(formattedKey, (String)value);
        } else if (value instanceof Integer) {
            editor.putInt(formattedKey, (Integer)value);
        }
        editor.apply();
    }

    private static void loadPreference(String key, Object dest) {
        String formattedKey = formatPreferenceKey(key);
        if (dest instanceof String) {
            dest = sPrefs.getString(formattedKey,null);
        } else if (dest instanceof Integer) {
            int value = sPrefs.getInt(formattedKey,Integer.MIN_VALUE);
            if (value==Integer.MIN_VALUE) {
                dest = null;
            } else {
                dest = Integer.valueOf(value);
            }
        }
    }

}
