package com.example.Test;

import by.deniotokiari.android.core.annotation.contract.Contract;
import by.deniotokiari.android.core.annotation.field.dbField;
import by.deniotokiari.android.core.annotation.field.dbString;
import by.deniotokiari.android.core.db.CoreContract;

@Contract
public class TestEntity extends CoreContract {

    @dbField
    @dbString
    public static final String TITLE = "TITLE";

    @dbField
    @dbString
    public static final String DESCRIPTION = "DESCRIPTION";

}
