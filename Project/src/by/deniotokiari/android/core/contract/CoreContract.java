package by.deniotokiari.android.core.contract;

import by.deniotokiari.android.core.annotation.field.dbAutoincrement;
import by.deniotokiari.android.core.annotation.field.dbField;
import by.deniotokiari.android.core.annotation.field.dbInteger;
import by.deniotokiari.android.core.annotation.field.dbPrimaryKey;

public class CoreContract {

    @dbField
    @dbAutoincrement
    @dbPrimaryKey
    @dbInteger
    public static final String _ID = "_id";

}
