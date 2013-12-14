package by.deniotokiari.core.helpers;

import android.content.ContentValues;
import android.database.Cursor;

public class CursorHelper {

    public static String get(Cursor cursor, String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    public static boolean isEmptyAndClosed(Cursor cursor) {
        return (cursor.getCount() == 0) && (cursor.isClosed());
    }

    public static ContentValues getContentValues(Cursor cursor) {
        ContentValues values = new ContentValues();

        for (String name : cursor.getColumnNames()) {
            values.put(name, get(cursor, name));
        }

        return values.keySet().size() == 0 ? null : values;
    }

}
