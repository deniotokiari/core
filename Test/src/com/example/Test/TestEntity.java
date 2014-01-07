package com.example.Test;

import by.deniotokiari.android.core.annotation.contract.Contract;
import by.deniotokiari.android.core.annotation.field.dbField;
import by.deniotokiari.android.core.annotation.field.dbString;
import by.deniotokiari.android.core.db.CoreContract;

@Contract
public class TestEntity extends CoreContract {

    @dbField
    @dbString
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
