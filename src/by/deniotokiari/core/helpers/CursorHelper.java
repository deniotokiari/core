package by.deniotokiari.core.helpers;

import android.database.Cursor;
import by.deniotokiari.core.content.CoreContract;
import by.deniotokiari.core.utils.L;

public class CursorHelper {

    public static String get(Cursor cursor, String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    public static boolean isEmptyAndClosed(Cursor cursor) {
        return (cursor.getCount() == 0) && (cursor.isClosed());
    }

    public static <T extends CoreContract> T getEntity(Cursor cursor, Class<T> clazz) {
        try {
            T entity = clazz.newInstance();
        } catch (InstantiationException e) {
            L.d
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

}
