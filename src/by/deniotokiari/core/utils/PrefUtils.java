package by.deniotokiari.core.utils;

import android.content.Context;
import by.deniotokiari.core.context.ContextHolder;
import by.deniotokiari.core.helpers.PreferencesHelper;

public class PrefUtils {

    private static final String PREF_NAME = "application_preferences";
    private static final int MODE = Context.MODE_PRIVATE;

    private static void put(String key, Object value) {
        PreferencesHelper.put(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, value);
    }

    public static void put(String key, Boolean value) {
        put(key, value);
    }

    public static void put(String key, Float value) {
        put(key, value);
    }

    public static void put(String key, Integer value) {
        put(key, value);
    }

    public static void put(String key, Long value) {
        put(key, value);
    }

    public static void put(String key, String value) {
        put(key, value);
    }

    public static boolean get(String key, boolean defValue) {
        return PreferencesHelper.getBoolean(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, defValue);
    }

    public static float get(String key, float defValue) {
        return PreferencesHelper.getFloat(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, defValue);
    }

    public static int get(String key, int defValue) {
        return PreferencesHelper.getInt(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, defValue);
    }

    public static long get(String key, long defValue) {
        return PreferencesHelper.getLong(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, defValue);
    }

    public static String get(String key, String defValue) {
        return PreferencesHelper.getString(ContextHolder.getInstance().getContext(), PREF_NAME, MODE, key, defValue);
    }



}
