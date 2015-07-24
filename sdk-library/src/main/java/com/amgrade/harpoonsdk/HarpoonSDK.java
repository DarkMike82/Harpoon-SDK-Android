package com.amgrade.harpoonsdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amgrade.harpoonsdk.rest.RestClient;
import com.amgrade.harpoonsdk.rest.model.user.User;

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
    private static Long sAppTokenExpires;

    //user data
    private static String sUserId;
    private static String sUserAuthCode;
    private static String sUserToken;
    private static Long sUserTokenExpires;

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
        sPrefs = sContext.getSharedPreferences(sContext.getPackageName()+"_harpoon", Context.MODE_PRIVATE); //PreferenceManager.getDefaultSharedPreferences(sContext);
        Log.d("HARPOONSDK", "connected prefs"+": "+sContext.getPackageName()+"_harpoon");
        sAppToken = loadStringPref("app.token");
        sUserId = loadStringPref("user.id");
        sUserAuthCode = loadStringPref("user.code");
        sUserToken = loadStringPref("user.token");
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
     * Save id and auth_code of validated user (after creation or login)
     * @param id user ID
     * @param auth_code authentication code (to exchange for token)
     */
    public static void setUserId(String id, String auth_code) {
        sUserId = id;
        sUserAuthCode = auth_code;
        saveToPrefs("user.id", sUserId);
        saveToPrefs("user.code", sUserAuthCode);
    }

    /**
     * Save full data of current user (after creation, login or update)
     * @param userData String representation of Json containing full user data
     */
    public static void setUser(String userData) {
        saveToPrefs("user", userData);
    }

    /**
     * Remove user data on logout;
     */
    public static void clearUser() {
        sUserId = null;
        removePreference("user.id");
        sUserToken = null;
        removePreference("user.token");
        sUserAuthCode = null;
        removePreference("user.code");
    }

    /**
     * Save token (for later use in auth).
     * @param type either {@link #TOKEN_APP} or {@link #TOKEN_USER}.
     * @param token token value.
     */
    public static void setToken(String type, String token, Long token_expires) {
        if (type.contentEquals(TOKEN_APP)) {
            sAppToken = token;
            saveToPrefs("app.token", token);
            saveToPrefs("app.token.expires", token_expires);
        } else if (type.contentEquals(TOKEN_USER)) {
            sUserToken = token;
            saveToPrefs("user.token", token);
            saveToPrefs("user.token.expires", token_expires);
            //clear unneeded auth code
            sUserAuthCode = null;
            removePreference("user.code");
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

    public static boolean isAppTokenExpired() {
        if (sAppToken==null) {
            return true;
        }
        Long app_token_expires = loadLongPref("app.token.expires");
        if (System.currentTimeMillis()>app_token_expires) {
            return true;
        }
        return false;
    }

    public static User getCurrentUser() {
        String userData = loadStringPref("user");
        User currentUser = RestClient.getGson().fromJson(userData, User.class);
        return currentUser;
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

    public static boolean isUserTokenExpired() {
        if (sUserToken==null) {
            return true;
        }
        Long user_token_expires = loadLongPref("user.token.expires");
        if (System.currentTimeMillis()>user_token_expires) {
            return true;
        }
        return false;
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
        return String.format("%s:%s:%s", DOMAIN, sContext.getPackageName(), key);
    }

    private static void saveToPrefs(String key, Object value) {
        String formattedKey = formatPreferenceKey(key);
        SharedPreferences.Editor editor = sPrefs.edit();
        if (value instanceof String) {
            editor.putString(formattedKey, (String)value);
        } else if (value instanceof Integer) {
            editor.putInt(formattedKey, (Integer)value);
        } else if (value instanceof Long) {
            editor.putLong(formattedKey, (Long) value);
        }
        boolean saved = editor.commit();//apply();
        Log.d("HARPOONSDK", "saved "+formattedKey+": "+saved+", "+value);
    }

    private static String loadStringPref(String key) {
        String formattedKey = formatPreferenceKey(key);
        String s = sPrefs.getString(formattedKey, null);
        Log.d("HARPOONSDK", "loaded "+formattedKey+": "+s);
        return s;
    }

    private static int loadIntPref(String key) {
        String formattedKey = formatPreferenceKey(key);
        int i = sPrefs.getInt(formattedKey, Integer.MIN_VALUE);
        Log.d("HARPOONSDK", "loaded "+formattedKey+": "+i);
        return i;
    }

    private static long loadLongPref(String key) {
        String formattedKey = formatPreferenceKey(key);
        long l = sPrefs.getLong(formattedKey, Long.MIN_VALUE);
        Log.d("HARPOONSDK", "loaded "+formattedKey+": "+l);
        return l;
    }

    /*private static void loadPreference(String key, Class<?> cl) {
        String formattedKey = formatPreferenceKey(key);
        if (cl instanceof Class<String>) {
            dest = sPrefs.getString(formattedKey,null);
            Log.d("HARPOONSDK", "loaded "+formattedKey+": "+dest);
        } else if (dest instanceof Integer) {
            int value = sPrefs.getInt(formattedKey,Integer.MIN_VALUE);
//            if (value==Integer.MIN_VALUE) {
//                dest = null;
//            } else {
                dest = Integer.valueOf(value);
                Log.d("HARPOONSDK", "loaded "+formattedKey+": "+dest);
//            }
        } else if (dest instanceof Long) {
            long value = sPrefs.getLong(formattedKey, Long.MIN_VALUE);
//            if (value==Long.MIN_VALUE) {
//                dest = null;
//            } else {
                dest = Long.valueOf(value);
                Log.d("HARPOONSDK", "loaded "+formattedKey+": "+dest);
//            }
        }
    }*/

    private static void removePreference(String key) {
        String formattedKey = formatPreferenceKey(key);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.remove(formattedKey);
        boolean saved = editor.commit();//apply();
        Log.d("HARPOONSDK", "removed " + formattedKey + ": " + saved);
    }

}
