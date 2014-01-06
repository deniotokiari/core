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

    private static final String TEMPLATE_SELECT_DISTINCT_TABLE = "SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '%s'";
    private static final String TEMPLATE_CREATE_TABLE = "CREATE TABLE %s (%s)";
    private static final String TEMPLATE_FIELD = "%s %s";
    private static final String TEMPLATE_PRIMARY_KEY = "%s %s PRIMARY KEY";
    private static final String TEMPLATE_UNIQUE = "UNIQUE (%s)";

    public static String getCreateTableString(Class<?> contract) {
        List<String> fieldsSql = new ArrayList<String>();
        List<String> uniqueFieldsSql = new ArrayList<String>();
        List<Field>  fields = Arrays.asList(contract.getFields());

        for (Field field : fields) {
            if (isPrimaryKeyField(field)) {
                fieldsSql.add(0, getFieldSql(TEMPLATE_PRIMARY_KEY, field));
            } else if (isUniqueField(field)) {
                uniqueFieldsSql.add(field.getName());
            } else {
                fieldsSql.add(getFieldSql(TEMPLATE_FIELD, field));
            }
        }

        if (uniqueFieldsSql.size() > 0) {
            fieldsSql.add(String.format(TEMPLATE_UNIQUE, StringUtils.join(uniqueFieldsSql, ", ")));
        }

        return String.format(TEMPLATE_CREATE_TABLE, ContractHelper.getTableName(contract), StringUtils.join(fieldsSql, ", "));
    }

    private static boolean isPrimaryKeyField(Field field) {
        return field.getAnnotation(dbPrimaryKey.class) != null;
    }

    private static boolean isUniqueField(Field field) {
        return field.getAnnotation(dbUnique.class) != null;
    }

    private static String getFieldSql(String template, Field field) {
        String result = "";
        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation instanceof dbBoolean) {
                result += "BOOLEAN";
            } else if (annotation instanceof dbVarchar) {
                result += "VARCHAR";
            } else if (annotation instanceof dbString) {
                result += "STRING";
            } else if (annotation instanceof dbInteger) {
                result += "INTEGER";
            } else if (annotation instanceof dbLong) {
                result += "BIGINT";
            } else {
                result += "VARCHAR";
            }

            if (annotation instanceof dbAutoincrement) {
                result += " " + "AUTOINCREMENT";
            }
        }

        return String.format(template, field.getName(), result);
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
