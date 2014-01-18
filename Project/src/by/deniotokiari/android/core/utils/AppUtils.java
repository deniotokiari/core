package by.deniotokiari.android.core.utils;

import android.content.Context;

public class AppUtils {

    public static Object get(Context context, String key) {
        Object service = context.getSystemService(key);
        if (service == null) {
            service = context.getApplicationContext().getSystemService(key);
        }
        return service;
    }

}
