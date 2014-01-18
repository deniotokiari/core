package by.deniotokiari.android.core.helper;

import android.content.ContentValues;
import android.database.Cursor;
import by.deniotokiari.android.core.annotation.field.*;
import by.deniotokiari.android.core.utils.DBUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ContentValuesHelper {

    public static ContentValues convert(Cursor cursor, Class<?> entity) {
        ContentValues values = new ContentValues();
        Field[] fields = entity.getFields();

        for (Field field : fields) {
            if (field.getAnnotation(dbField.class) == null) {
                continue;
            }

            Annotation[] annotations = field.getAnnotations();
            String key = DBUtils.getName(field);

            for (Annotation annotation : annotations) {
                if (annotation instanceof dbBoolean) {
                    values.put(key, CursorHelper.getBoolean(cursor, key));
                } else if (annotation instanceof dbInteger) {
                    values.put(key, CursorHelper.getInteger(cursor, key));
                } else if (annotation instanceof dbLong) {
                    values.put(key, CursorHelper.getLong(cursor, key));
                } else if (annotation instanceof dbString) {
                    values.put(key, CursorHelper.getString(cursor, key));
                } else if (annotation instanceof dbVarchar) {
                    values.put(key, CursorHelper.getString(cursor, key));
                }
            }
        }

        return values.size() > 0 ? values : null;
    }

}
