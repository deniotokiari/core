package by.deniotokiari.android.core.utils;

import java.util.Collection;

public class StringUtils {

    public static <T> String join(Collection<T> items, String joiner) {
        String result = "";
        for (T item : items) {
            if (item == null) {
                continue;
            }
            result += String.valueOf(item) + joiner;
        }
        return substring(result, joiner.length());
    }

    public static <T> String join(T[] items, String joiner) {
        String result = "";
        for (T item : items) {
            if (item == null) {
                continue;
            }
            result += String.valueOf(item) + joiner;
        }
        return substring(result, joiner.length());
    }

    private static String substring(String str, int n) {
        return str.substring(0, str.length() - n);
    }

}
