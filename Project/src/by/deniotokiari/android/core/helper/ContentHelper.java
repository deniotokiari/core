package by.deniotokiari.android.core.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import by.deniotokiari.android.core.contract.CoreRequestContract;

import java.util.ArrayList;
import java.util.List;

public class ContentHelper {

    public static Cursor get(Context context, String sql, String[] args) {
        return context.getContentResolver().query(UriHelper.getUriSql(ContractHelper.getUri(CoreRequestContract.class)), null, sql, args, null);
    }

    public static ContentValues get(Context context, Class<?> entity, String selection, String[] selectionArgs) {
        Cursor cursor = context.getContentResolver().query(ContractHelper.getUri(entity), null, selection, selectionArgs, null);
        ContentValues values = null;

        if (cursor.moveToFirst()) {
            values = ContentValuesHelper.convert(cursor, entity);
        }

        cursor.close();
        return (values != null && values.size() > 0) ? values : null;
    }

    public static List<ContentValues> getAll(Context context, Class<?> entity, String selection, String[] selectionArgs) {
        Cursor cursor = context.getContentResolver().query(ContractHelper.getUri(entity), null, selection, selectionArgs, null);
        List<ContentValues> result = new ArrayList<ContentValues>();

        if (cursor.moveToFirst()) {
            ContentValues values;
            do {
                values = ContentValuesHelper.convert(cursor, entity);
                if (values != null) {
                    result.add(values);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return result.size() > 0 ? result : null;
    }

    public static Uri add(Context context, Class<?> entity, ContentValues values, boolean notify) {
        Uri uri = ContractHelper.getUri(entity);
        if (notify) {
            uri = UriHelper.getNotificationUri(uri);
        }

        return context.getContentResolver().insert(uri, values);
    }

    public static int addAll(Context context, Class<?> entity, ContentValues[] values, boolean notify) {
        Uri uri = ContractHelper.getUri(entity);
        if (notify) {
            uri = UriHelper.getNotificationUri(uri);
        }

        return context.getContentResolver().bulkInsert(uri, values);
    }

    public static int delete(Context context, Class<?> entity, String selection, String[] selectionArgs, boolean notify) {
        Uri uri = ContractHelper.getUri(entity);
        if (notify) {
            uri = UriHelper.getNotificationUri(uri);
        }

        return context.getContentResolver().delete(uri, selection, selectionArgs);
    }

    public static int update(Context context, Class<?> entity, ContentValues values, String selection, String[] selectionArgs, boolean notify) {
        Uri uri = ContractHelper.getUri(entity);
        if (notify) {
            uri = UriHelper.getNotificationUri(uri);
        }

        return context.getContentResolver().update(uri, values, selection, selectionArgs);
    }

}
