package by.deniotokiari.android.core.db;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDataBase {

    public Cursor query(Class<?> contract, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public Cursor rawQuery(Class<?> contract, String sql, String[] selectionArgs);

    public long insert(Class<?> contract, ContentValues values);

    public int bulkInsert(Class<?> contract, ContentValues[] values);

    public int delete(Class<?> contract, String selection, String[] selectionArgs);

    public int update(Class<?> contract, ContentValues values, String selection, String[] selectionArgs);

}
