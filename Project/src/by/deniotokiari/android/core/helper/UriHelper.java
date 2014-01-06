package by.deniotokiari.android.core.helper;

import android.net.Uri;

public class UriHelper {

    public static final String KEY_NOTIFICATION_URI = "notify";

    public static boolean isHasKey(Uri uri, String key) {
        return uri.getQuery() != null && uri.getQuery().contains(key);
    }

    public static Uri getUriWithKey(Uri uri, String key) {
        return Uri.parse(uri + (uri.getQuery() == null ? "?" : "&") + key);
    }

    public static Uri getNotificationUri(Uri uri) {
        return getUriWithKey(uri, KEY_NOTIFICATION_URI);
    }

    public static boolean isNotificationUri(Uri uri) {
        return isHasKey(uri, KEY_NOTIFICATION_URI);
    }

    public static Uri getUriWithoutQuery(Uri uri) {
        if (uri.getQuery() != null) {
            return Uri.parse(uri.toString().split("\\?")[0]);
        } else {
            return uri;
        }
    }
}
