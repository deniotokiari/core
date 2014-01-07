package by.deniotokiari.android.core.db;

import android.content.ContentValues;
import android.database.Cursor;
import by.deniotokiari.android.core.annotation.field.dbAutoincrement;
import by.deniotokiari.android.core.annotation.field.dbField;
import by.deniotokiari.android.core.annotation.field.dbInteger;
import by.deniotokiari.android.core.annotation.field.dbPrimaryKey;

import java.util.List;

public class CoreContract {

    @dbField
    @dbAutoincrement
    @dbPrimaryKey
    @dbInteger
    public static int _id;

    public void init(Cursor cursor) {

    }

    public ContentValues getContentValues() {

    }

}
