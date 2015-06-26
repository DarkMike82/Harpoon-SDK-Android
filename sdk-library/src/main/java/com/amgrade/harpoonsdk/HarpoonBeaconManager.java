package com.amgrade.harpoonsdk;

import android.os.Build;

/**
 * Created by Michael Dontsov on 26.06.15.
 */
public class HarpoonBeaconManager {
    private static final int BEACON_API_LEVEL = 19;

    public static boolean isAdvertisingAvailable() {
        if (Build.VERSION.SDK_INT < BEACON_API_LEVEL) {
            return false;
        } else {
            return true;
        }
    }

}
