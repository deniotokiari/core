package by.deniotokiari.core.helpers;

import android.content.ContentValues;
import by.deniotokiari.core.annotations.db.DBAutoincrement;

import java.lang.reflect.Field;

public class ContentValuesHelper {

    private static <T> void put(ContentValues contentValues, String key, T value) {
        if (value instanceof String) {
            contentValues.put(key, (String) value);
        } else if (value instanceof Integer) {
            contentValues.put(key, (Integer) value);
        } else if (value instanceof Short) {
            contentValues.put(key, (Short) value);
        } else if (value instanceof Long) {
            contentValues.put(key, (Long) value);
        } else if (value instanceof byte[]) {
            contentValues.put(key, (byte[]) value);
        } else if (value instanceof Float) {
            contentValues.put(key, (Float) value);
        } else if (value instanceof Boolean) {
            contentValues.put(key, (Boolean) value);
        } else if (value instanceof Byte) {
            contentValues.put(key, (Byte) value);
        } else if (value instanceof Double) {
            contentValues.put(key, (Double) value);
        }
    }

    private static boolean isAutoincrement(Field field) {
        return field.getAnnotation(DBAutoincrement.class) != null;
    }

    public static ContentValues getContentValues(Class<?> contract, Object... args) {
        Field[] fields = contract.getFields();
        ContentValues values = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            if (isAutoincrement(fields[i]) || args[i] == null) {
                continue;
            }

            put(values, fields[i].getName(), args[i]);
        }
        return values;
    }

}

