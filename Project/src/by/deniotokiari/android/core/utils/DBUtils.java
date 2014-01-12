package by.deniotokiari.android.core.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import by.deniotokiari.android.core.annotation.contract.Contract;
import by.deniotokiari.android.core.annotation.field.*;
import by.deniotokiari.android.core.helper.ContractHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBUtils {

    private static final String TEMPLATE_SELECT_DISTINCT_TABLE = "SELECT DISTINCT tbl_name from sqlite_master where tbl_name = %s";
    private static final String TEMPLATE_CREATE_TABLE = "CREATE TABLE %s (%s)";
    private static final String TEMPLATE_FIELD = "%s %s";
    private static final String TEMPLATE_PRIMARY_KEY = "%s %s PRIMARY KEY";
    private static final String TEMPLATE_UNIQUE = "UNIQUE (%s)";

    public static String getCreateTableString(Class<?> contract) {
        List<String> fieldsSql = new ArrayList<String>();
        List<String> uniqueFieldsSql = new ArrayList<String>();
        List<Field>  fields = Arrays.asList(contract.getFields());

        for (Field field : fields) {
            if (!isDbField(field)) {
                continue;
            }

            if (isPrimaryKeyField(field)) {
                fieldsSql.add(0, getFieldSql(TEMPLATE_PRIMARY_KEY, field));
            } else if (isUniqueField(field)) {
                uniqueFieldsSql.add(getName(field));
            } else {
                fieldsSql.add(getFieldSql(TEMPLATE_FIELD, field));
            }
        }

        if (uniqueFieldsSql.size() > 0) {
            fieldsSql.add(String.format(TEMPLATE_UNIQUE, StringUtils.join(uniqueFieldsSql, ", ")));
        }

        return String.format(TEMPLATE_CREATE_TABLE, ContractHelper.getTableName(contract), StringUtils.join(fieldsSql, ", "));
    }

    private static boolean isDbField(Field field) {
        return field.getAnnotation(dbField.class) != null;
    }

    private static boolean isPrimaryKeyField(Field field) {
        return field.getAnnotation(dbPrimaryKey.class) != null;
    }

    private static boolean isUniqueField(Field field) {
        return field.getAnnotation(dbUnique.class) != null;
    }

    private static String getName(Field field) {
        String value = null;

        try {
            value = (String) field.get(value);
        } catch (IllegalAccessException e) {
            return null;
        }

        return value;
    }

    private static String getFieldSql(String template, Field field) {
        List<String> result = new ArrayList<String>();
        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation instanceof dbBoolean) {
                result.add(0, String.format(template, getName(field), "BOOLEAN"));
            } else if (annotation instanceof dbVarchar) {
                result.add(0, String.format(template, getName(field), "VARCHAR"));
            } else if (annotation instanceof dbString) {
                result.add(0, String.format(template, getName(field), "STRING"));
            } else if (annotation instanceof dbInteger) {
                result.add(0, String.format(template, getName(field), "INTEGER"));
            } else if (annotation instanceof dbLong) {
                result.add(0, String.format(template, getName(field), "BIGINT"));
            }

            if (annotation instanceof dbAutoincrement) {
                result.add("AUTOINCREMENT");
            }
        }

        return StringUtils.join(result, " ");
    }

    public static boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (db == null || tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            db.beginTransaction();
            cursor = db.rawQuery(
                    String.format(TEMPLATE_SELECT_DISTINCT_TABLE, tableName),
                    null);
            if (cursor.getCount() > 0) {
                db.setTransactionSuccessful();
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.endTransaction();
        }
    }

}
