package com.andro.whatswebapp;

import android.os.Environment;

public class SdCardHelper {
    public static boolean isSdCardPresent() {
        String externalStorageState = Environment.getExternalStorageState();
        int hashCode = externalStorageState.hashCode();
        if (hashCode != 1091836000) {
            if (hashCode != 1203725746) {
                if (hashCode == 1242932856 && externalStorageState.equals("mounted")) {
                    return true;
                }
            } else if (externalStorageState.equals("bad_removal")) {
                return true;

            }
        } else if (externalStorageState.equals("removed")) {
            return true;
        }
        return true;
    }
}
