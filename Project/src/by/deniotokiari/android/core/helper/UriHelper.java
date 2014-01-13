package by.deniotokiari.android.core.helper;

import android.net.Uri;
import by.deniotokiari.android.core.utils.StringUtils;

public class UriHelper {

    public static final String KEY_NOTIFICATION_URI = "notify";
    public static final String KEY_SQL = "sql";

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

    public static boolean isWithSql(Uri uri) {
        return isHasKey(uri, KEY_SQL);
    }

    public static String getSqlFromUri(Uri uri) {
        return uri.getQueryParameter(KEY_SQL);
    }

    public static Uri getUriWithSql(Class<?> contract, String sql, String... args) {
        return getUriWithKey(ContractHelper.getUri(contract), KEY_SQL + "=" + (args == null ? sql : StringUtils.fill(sql, "\\?", args)));
    }

}
