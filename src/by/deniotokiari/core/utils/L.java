package by.deniotokiari.core.utils;

import android.util.Log;

public class L {

    public static final String TAG_LOG = "LOG";

    public static void d(String str) {
        Log.d(TAG_LOG, str);
    }

    public static void d(String tag, String str) {
        Log.d(tag, str);
    }

}
