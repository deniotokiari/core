package by.deniotokiari.android.core.helper;

import android.database.Cursor;

public class CursorHelper {

    public static String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public static Integer getInteger(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    public static Long getLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }

    public static Boolean getBoolean(Cursor cursor, String column) {
        return null;
    }

}
