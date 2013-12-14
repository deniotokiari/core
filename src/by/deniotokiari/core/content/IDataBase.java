package by.deniotokiari.core.content;

import android.content.ContentValues;

public interface IDataBase<T> {

    public long addItem(Class<?> contract, ContentValues value);

    public int addItems(Class<?> contract, ContentValues[] values);

    public T getItems(Class<?> contract, String selection,
                      String[] selectionArgs, String orderBy);

    public T rawQuery(Class<?> contract, String sql,
                      String[] selectionArgs);

    public int deleteItems(Class<?> contract, String where,
                         String[] whereArgs);

}
